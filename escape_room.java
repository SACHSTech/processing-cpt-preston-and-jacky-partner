import processing.core.PApplet;
import processing.core.PImage;
import java.util.Random;
import java.util.Scanner;

public class escape_room extends PApplet {

  // level image variable 
  PImage[] imgLevel;
  PImage[] imgLevelCollision;

  // player image movement variables 
  PImage[] imgPlayerLeft;
  PImage[] imgPlayerRight;
  PImage[] imgPlayerUp;
  PImage[] imgPlayerDown;

  // paper on desk popup
  PImage[] imgPage;

  // boolean for verification for safe combo
  boolean blnVerify = false;

  // safe popup
  PImage imgSafe;

  // boolean to detect if the player has interacted with the page on the desk 
  boolean blnPage, blnSafe = false;

  // page number
  int intPageNumber = 0;

  // player direction
  String strDirection = "Down";

  // game starting boolean
  boolean blnGameStarting = true;

  // boolean for oxygen meter
  boolean blnOxygenMeter = false;

  // game ending boolean 
  boolean blnGameEnding = false;

  // string for safe combination
  String strCode = "";

  // next level boolean
  boolean[] blnNextLevel = {true,false,false,false,false,false};

  // game O2 meter
  int intOxygenMeter = 100;
  int intTotalOxygen;

  // number of levels
  int intNumLevels = 10;

  // current level
  int intLevel = 3;

  // password for level 2
  String strPassword = "";

  // health
  int intHealth = 100;

  // number of frames for each player animation 
  int intNumFrames = 4;
  int intMoveFrames = 0;

  // player position 
  int intPlayerX = 300;
  int intPlayerY = 300;
	
  // movement booleans 
  boolean blnUp, blnDown, blnLeft, blnRight, blnInteract, blnLeftArrow, blnRightArrow;

  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {
    
    size(672, 672);

  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {

    // setting up image variable for levels
    imgLevel = new PImage[intNumLevels];

    // setting up image variable for level collisions
    imgLevelCollision = new PImage[intNumLevels];

    // setting up image variables for down movement animation
    imgPlayerDown = new PImage[intNumFrames];

    // setting up image variable for left movement animation
    imgPlayerLeft = new PImage[intNumFrames];

    // setting up image variable for right movement animation 
    imgPlayerRight = new PImage[intNumFrames];

    // setting up image variable for up movement animation 
    imgPlayerUp = new PImage[intNumFrames];

    imgPage = new PImage[2];

    for (int i = 0; i < intNumLevels; i++) {

      imgLevel[i] = loadImage("escape_room/levels/level" + i + ".png"); 

    }

    for (int i = 0; i < intNumLevels; i++) {

      imgLevelCollision[i] = loadImage("escape_room/levels/collisions/level" + i + ".png"); 

    }

    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerLeft[i] = loadImage("escape_room/player/playerLeft" + i + ".png");
      imgPlayerLeft[i].resize(42,54);

    }
  
  
    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerRight[i] = loadImage("escape_room/player/playerRight" + i + ".png");
      imgPlayerRight[i].resize(42,54);

    }
      
    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerUp[i] = loadImage("escape_room/player/playerUp" + i + ".png");
      imgPlayerUp[i].resize(42,54);

    }
  
    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerDown[i] = loadImage("escape_room/player/playerDown" + i + ".png");
      imgPlayerDown[i].resize(42,54);

    }

    imgPage[0] = loadImage("escape_room/popups/page" + 0 + ".png");
    imgPage[1] = loadImage("escape_room/popups/page" + 1 + ".png");

    imgSafe = loadImage("escape_room/popups/safe.png");

  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {

    if (blnGameStarting == true && intOxygenMeter > 0) {
     
      StartingScreen();
      drawCollisionMaps();
      playerMovementAndCollisions();
      playerInteractions();
      drawMaps();
      oxygenMeter();
      playerUpdate();
      nextLevel();

    } else if (blnGameEnding == true) {

      background(0);
      textSize(50);
      fill(255);
      text("ggs wp",width / 2,height / 2);

    // to draw the end screen once the player has died 
    } else {

      background(0);
      textSize(50);
      fill(255);
      text("mission failed sucessfully, we'll get'em next time",width / 2,height / 2);

    }

  }

  public void StartingScreen() {
  
  }

  /**
   * draws the needed collision map to match with the player map
   */
  public void drawCollisionMaps() {

    // draws the collision maps 
    image(imgLevelCollision[intLevel],0,0);

    // draws and invisible barrier for the player so they can not leave the level before finishing it properly 
    if (intLevel == 3 && blnNextLevel[1] == false) {

      fill(0);
      rect(0,0,8,height);

    } else if (intLevel == 5 && blnNextLevel[2] == false) {

      fill(0);
      rect(0,0,width,8);

    }

  }

  /**
   * draws the oxygen meter
   */
  public void oxygenMeter() {

    // only starts the meter once they have left the tutorial level 
    if (blnOxygenMeter == true) {

      fill(173, 216, 230);
      noStroke();
      rect(640,640,20, -intOxygenMeter);
      stroke(0,0,0);
      noFill();
      rect(640,540,20,100);
    
      // slowly ticks away at the oxygen meter 
      if (frameCount % 60 == 0) {

        intOxygenMeter -= 1;

      }
    }

    // only starts the oxygen meter once the player has made their way out of the tutorial level and into the real first level 
    if (intLevel == 3) {

      blnOxygenMeter = true;
    
    }
  }

  /**
   * movement for the player and checks for collisions
   */
  public void playerMovementAndCollisions() {

    if (blnPage == false && blnSafe == false)  {
      // left player collision detection
      if (blnLeft == true && (get(intPlayerX - 8, intPlayerY + 54) != -1.6777216E7 && get(intPlayerX - 8, intPlayerY + 54) != -16776961)) {

        intPlayerX -= 8;
        
      } 
      
      // right player collision detection 
      if (blnRight == true && (get(intPlayerX + 50, intPlayerY + 54) != -1.6777216E7 && get(intPlayerX + 50, intPlayerY + 54) != -16776961)) {

        intPlayerX += 8;
        
      } 
      
      // up player collision detection
      if (blnUp == true && (get(intPlayerX, intPlayerY + 28) != -1.6777216E7 && get(intPlayerX + 28, intPlayerY + 28) != -1.6777216E7 && get(intPlayerX, intPlayerY + 28) != -16776961 && get(intPlayerX + 28, intPlayerY + 28) != -16776961)) {
          
        intPlayerY -= 8;
        
      } 
      
      // down player collision detection 
      if (blnDown == true && (get(intPlayerX, intPlayerY + 62) != -1.6777216E7 && get(intPlayerX + 28, intPlayerY + 62) != -1.6777216E7 && get(intPlayerX, intPlayerY + 62) != -16776961 && get(intPlayerX + 28, intPlayerY + 44) != -16776961)) {

        intPlayerY += 8;
        
      }
    }
  }

  /**
   * player interactions with objects 
   */
  public void playerInteractions() {

    if (blnInteract == true) {

      if (intLevel == 3) {

        if (intPlayerY < height / 2) {

          if (get(intPlayerX, intPlayerY - 8) == -16776961 || get(intPlayerX + 64, intPlayerY) == -16776961) {

              if (blnPage == true) {
  
                blnPage = false;
                delay(300);
  
              } else if (blnPage == false) {
  
                blnPage = true;
                delay(300);
  
              }

          }

        } else {

          if (get(intPlayerX, intPlayerY + 64) == -16776961 || get(intPlayerX - 8, intPlayerY) == -16776961 || get(intPlayerX, intPlayerY - 8) == -16776961 ) {

            if (blnSafe == true) {
  
                blnSafe = false;
                delay(300);
  
            } else if (blnSafe == false) {

                blnSafe = true;
                delay(300);
  
            }
          }
        }

      } else if (intLevel == 5) {

        // uses position and colour detection to determine the key that the player is standing on top of. It will then print out the specific key onto the screen 
        if (intPlayerY <= 261) {

          // coordinates for the letter A
          if (intPlayerX <= 115 && (get(intPlayerX + 30, intPlayerY + 56) == -3584 || get(intPlayerX,intPlayerY + 56) == -3584)) {
            
            strPassword += 'a';
            delay(300);

          // coordinates for the letter D
          } else if (intPlayerX > 115 && intPlayerX < 237 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

            strPassword += 'd';
            delay(300);
            
          // coordinates for the letter R
          } else if (intPlayerX > 237 && intPlayerX < 350 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) { 
          
            strPassword += "r";
            delay(300);

          // coordinates for the letter G
          } else if (intPlayerX > 350 && intPlayerX < 503 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

            strPassword += 'g';
            delay(300);

          // coordinates for the letter J
          } else if (intPlayerX > 503 && intPlayerX < 614 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

            strPassword += 'j';
            delay(300);

          }

        } else if (intPlayerY > 261 && intPlayerY <= 375) {

          // coordinates for the letter B
          if (intPlayerX < 115 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

            strPassword += 'b';
            delay(300);

          // coordinates for the letter E
          } else if (intPlayerX > 115 && intPlayerX < 237 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

            strPassword += 'e';
            delay(300);

          // coordinates for deletion key
          } else if (intPlayerX > 237 && intPlayerX < 380 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) { 
          
            strPassword = "";
            delay(300);

          // coordinates for the letter H
          } else if (intPlayerX > 237 && intPlayerX < 503 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

            strPassword += 'h';
            delay(300);

          // coordinates for the letter K
          } else if (intPlayerX > 503 && intPlayerX < 614 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

            strPassword += 'k';
            delay(300);

          }

        } else if (intPlayerY > 375 && intPlayerY < 490) {
   
          // coordinates for the letter C
          if (intPlayerX < 115 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

            strPassword += 'c';
            delay(300);

          // coordinates for the letter F
          } else if (intPlayerX > 115 && intPlayerX < 237 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

            strPassword += 'f';
            delay(300);

          // coordinates for the letter O
          } else if (intPlayerX > 237 && intPlayerX < 350 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) { 
          
            strPassword += "o";
            delay(300);

            
          // coordinates for the letter I
          } else if (intPlayerX > 237 && intPlayerX < 503 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

            strPassword += 'i';
            delay(300);

          // coordinates for the letter L
          } else if (intPlayerX > 503 && intPlayerX < 614 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

            strPassword += 'l';
            delay(300);

          }
        
        }

      } else if (intLevel == 7) {

      } else if (intLevel == 9) {

      }

    } 

  }

  /**
   * draws the nescessary maps for the level that the player is on 
   */
  public void drawMaps() {

    // draws out the correct room depending on the level the player is on 
    image(imgLevel[intLevel],0,0);

  }

  /**
   * updates the player model depending on the direction that the player is moving 
   */
  public void playerUpdate() {

    if (blnUp == true) {

      image(imgPlayerUp[intMoveFrames], intPlayerX, intPlayerY);

      strDirection = "Up";

    } else if (blnDown == true) {

      image(imgPlayerDown[intMoveFrames], intPlayerX, intPlayerY);

      strDirection = "Down";

    } else if (blnLeft == true) {

      image(imgPlayerLeft[intMoveFrames], intPlayerX, intPlayerY);

      strDirection = "Left";

    } else if (blnRight == true) {

      image(imgPlayerRight[intMoveFrames], intPlayerX, intPlayerY);

      strDirection = "Right";

    }

    intMoveFrames = (intMoveFrames + 1) % intNumFrames;
    
    if (blnMoving() == false) {

      if (strDirection.equals("Up")) {

        image(imgPlayerUp[0], intPlayerX, intPlayerY);

      } else if (strDirection.equals("Down")) {

        image(imgPlayerDown[0], intPlayerX, intPlayerY);
    
      } else if (strDirection.equals("Left")) {

        image(imgPlayerLeft[0], intPlayerX, intPlayerY);

      } else if (strDirection.equals("Right")) {

        image(imgPlayerRight[0], intPlayerX, intPlayerY);

      }
    }
  }

  /**
   * determiens if the level has been properly completed and then allows the player to move onto the next one 
   */
  public void nextLevel() {

    // checks to see if the level has been completed or not 
    if (intLevel == 1 || intLevel == 0) {

      // during the tutorial level, the player will always be able to go through the doors, however, once they entre the first room, they are no logner allowed to go back 
      blnNextLevel[0] = true;

    
    } else if (intLevel == 3) {

      // sees if the player is interacting with the paper on the desk 
      if (blnPage == true) {

        // desk pop up 
        image(imgPage[intPageNumber],CENTER,CENTER);

        // turns the page one to the left         
        if (blnLeftArrow == true) {

          intPageNumber = 0;

        }  
        
        // turns the page one to the right 
        if (blnRightArrow == true) {

          intPageNumber = 1;

        }

      }

      // checks if the user has interacted with the safe 
      if (blnSafe == true) {

        // safe pop up 
        image(imgSafe,CENTER,CENTER);

        // checks if the user has clicked the check mark button 
        if (blnVerify == true) {

          // checks to see if the user inputted the correct digits
          if (strCode.equals("4132")) {

            // changes the output from the digits to OPEN to let the user know they got the code correct
            strCode = "OPEN";

            blnVerify = false;
            blnNextLevel[1] = true;   
            
            // displasy the new message
            fill(173, 216, 230);       
            textSize(130);
            text(strCode, 105, 220);
   

          } else {

            // changes the variable to WRONG to let the user know they got the code wrong and must try again 
            strCode = "WRONG";
            blnVerify = false;
            fill(173, 216, 230);       
            textSize(130);
            text(strCode, 105, 220);
          
          } 

        } else {

          // displays the numbers that the user inputted into the safe
          fill(173, 216, 230);       
          textSize(130);
          text(strCode, 105, 220);
          
          // if the numbers were wrong, it will show wrong for .5 seconds and then set the code to empty to allow them to try again 
          if (strCode.equals("WRONG")) {

            delay(500);
            strCode = "";

          // if the numbers were right, it show open for .5 seconds and then it will open the safe door, which will remove the pop keypad for the safe
          } else if (strCode.equals("OPEN")) {

            delay(500);
            blnSafe = false;

          }
        }
      }

    } else if (intLevel == 5) {

      // displays what the user has tyed onto the floor 
      fill(0)
;     textSize(40);
      text(strPassword, (width / 2) - 20, height - 50);

      if (strPassword.equals("fabroa") == true) {

        blnNextLevel[2] = true;

      } else {

        blnNextLevel[2] = false;

      }
    }

    // allows players to go to the next level once they have completed it
    if ((intLevel == 0 | intLevel == 1) && intPlayerX < 16) {

      intLevel += 1;
      intPlayerX = 664;

    // checks to see if the player is proceeding to the next level and if they are able to, if they are it will change the map and change their location as well
    } else if (intLevel == 2 && intPlayerX < 600) {

      intLevel += 1;

    // keeps track if the level has been completed previously 
    } else if (intPlayerX <= 16 && intLevel == 3 && blnNextLevel[1] == true) {

      intLevel += 1;
      intPlayerX = 664;
      
    } else if (intPlayerY <= 16 && intLevel == 5 && blnNextLevel[2] == true) {

      intLevel += 1;
      intPlayerY = 664;

    } else if (intPlayerX >= 664 && intLevel == 7 && blnNextLevel[3] == true) {

      intLevel += 1;
      intPlayerX = 16;

    } else if (intPlayerY >= 664 && intLevel == 9 && blnNextLevel[4] == true) {

      intLevel += 1;
      intPlayerY = 16;

    }

    // allows players to walk to previously completed levels and walk back to new ones too using hallways 
     if ((intLevel == 1 || intLevel == 0) && intPlayerX > 664) {

      intLevel -= 1;
      intPlayerX = 16;

    } else if (intLevel == 4 && intPlayerX < 16) {

      intLevel += 1;
      intPlayerX = 664;

    } else if (intLevel == 4 && intPlayerX > 664) {
      
      intLevel -= 1;
      intPlayerX = 16;

    } else if (intLevel == 5 && intPlayerX > 664) {

      intLevel -= 1;
      intPlayerX = 16;

    } else if (intLevel == 6 && intPlayerY < 16) {

      intLevel += 1;
      intPlayerX = 664;

    } else if (intLevel == 6 && intPlayerY > 664) {

      intLevel -= 1;
      intPlayerY = 16;

    }
  }

  /**
   * detects which keys are pressed and then sets certain boolean values to true
   */
  public void keyPressed() {

    intMoveFrames = 0;

    if (key == 'a' || key =='A') {

      blnLeft = true;

    } 
    
    if (key == 'd' || key =='D') {
        
      blnRight = true;

    } 
    
    if (key == 'w' || key =='W') {
        
      blnUp = true;

    } 
    
    if (key == 's' || key =='S') {
        
      blnDown = true;

    } 

    if (key == 'e' || key == 'E') {

      blnInteract = true;

    }

    if (keyCode == LEFT) {

      blnLeftArrow = true;

    }

    if (keyCode == RIGHT) {

      blnRightArrow = true;

    }
  }

  /**
   * detects which keys are released and then sets the corresponding boolean value to false 
   */
  public void keyReleased() {

    if (key == 'a' || key =='A') {

      blnLeft = false;

    } if (key == 'd' || key =='D') {
      
      blnRight = false;

    } if (key == 'w' || key =='W') {
      
      blnUp = false;

    } if (key == 's' || key =='S') {
      
      blnDown = false;

    } if (key == 'e' || key == 'E') {

      blnInteract = false;

    }

    if (keyCode == LEFT) {

      blnLeftArrow = false;

    }

    if (keyCode == RIGHT) {

      blnRightArrow = false;

    }
  }

  /**
   * detects moues inputs 
   */
  public void mousePressed() {

    if (blnSafe) {

      // deletion key, placed outside the 2 if statement below becuaes it needs to accept input even when the passcode hits 6 digits long 
      if ((mouseX > 261 && mouseX < 335) && (mouseY > 506 && mouseY < 592)) {

        if (strCode.length() > 0) {

          strCode = strCode.substring(0, strCode.length() - 1);

        }
      }

      // verification key, placed outside the if statement below becuaes it needs to accept input een when the passcode hits 6 digits long 
      if ((mouseX > 96 && mouseX < 175) && (mouseY > 506 && mouseY < 592)) {

        blnVerify = true;

      }

      // makes the max digit password length to 6
      if (strCode.length() < 6) {
          
        // used to check for the y value of the first 3 keys in the first row 
        if (mouseY > 250 && mouseY < 340) {
          
          // x boundaries for the number 1 key
          if (mouseX > 96 && mouseX < 175) {

            strCode += '1';

          }

          // x boundaries for the number 2 key
          if (mouseX > 176 && mouseX < 260) {

            strCode += '2';

          }

          // x boundaries for the number 3 key
          if (mouseX > 261 && mouseX < 335) {

            strCode += '3';

          }

        // checks the y boundaries for the second row of keys 
        } else if (mouseY > 341 && mouseY < 420) {

          // x boundaries for the number 4 key
          if (mouseX > 96 && mouseX < 175) {

            strCode += '4';

          }

          // x boundaries for the number 5 key
          if (mouseX > 176 && mouseX < 260) {

            strCode += '5';

          }

          // x boundaries for the number 6 key
          if (mouseX > 261 && mouseX < 335) {

            strCode += '6';

          }

        // checks the y boundaries for the third row of keys 
        } else if (mouseY > 421 && mouseY < 505) {

          // x boundaries for the number 7 key
          if (mouseX > 96 && mouseX < 175) {

            strCode += '7';

          }

          // x boundaries for the number 8 key
          if (mouseX > 176 && mouseX < 260) {

            strCode += '8';

          }

          // x boundaries for the number 9 key
          if (mouseX > 261 && mouseX < 335) {

            strCode += '9';

          }

        // checks the boundary for the 0 key 
        } else if (mouseY > 506 && mouseY < 592) {

          // x boundaries for the number 0 key
          if (mouseX > 176 && mouseX < 260) {

            strCode += '0';

          }
        }
      }

      
    }
  }

  public boolean blnMoving() {
    return blnUp || blnDown || blnLeft || blnRight;
  }
}
