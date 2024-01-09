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

  // if combat is being used 
  PImage[] imgPlayerLeftAttack;
  PImage[] imgPlayerRightAttack;
  PImage[] imgPlayerUpAttack;
  PImage[] imgPlayerDownAttack;

  // player direction
  String strDirection = "Down";

  // game starting boolean
  boolean blnGameStarting = true;

  // next level boolean
  boolean blnNextLevel = false;

  // game O2 meter
  int intOxygenMeter;
  int intTotalOxygen;

  boolean blntouchingyellow = false;

  // number of levels
  int intNumLevels = 10;

  // current level
  int intLevel = 6;

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
  boolean blnUp, blnDown, blnLeft, blnRight, blnInteract;

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

    intOxygenMeter = 100;

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
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {

    if (blnGameStarting == true && intOxygenMeter > 0) {
     
      drawCollisionMaps();
      playerMovementAndCollisions();
      playerInteractions();
      //drawMaps();
      //OxygenMeter();
      playerUpdate();
      NextLevel();
      System.out.println(strPassword);
      System.out.println(blnNextLevel);
    } else {

    }

  }

  /**
   * draws the needed collision map to match with the player map
   */
  public void drawCollisionMaps() {

    // draws the collision maps 
    image(imgLevelCollision[intLevel],0,0);

    // prevents the player from leaving the map
    if (intLevel == 3 && blnNextLevel == false) {

      fill(0);
      rect(0,0,8,height);

    } else if (intLevel == 6 && blnNextLevel == false) {

      fill(0);
      rect(0,0,width,8);

    }

  }

  /**
   * draws the oxygen meter
   */
  public void OxygenMeter() {

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

  /**
   * movement for the player and checks for collisions
   */
  public void playerMovementAndCollisions() {
    
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

    if (intLevel == 1 || intLevel == 0) {

      blnNextLevel = true;

    }
    
    if (intPlayerX <= 16 && blnNextLevel == true) {

      intLevel += 1;
      intPlayerX = 664;
      blnNextLevel = false;
      
    } else if (intPlayerX > 664) {

      intLevel -= 1;
      intPlayerX = 16;

    } 

    // prevents player from going back into the tutorial level 
    if (intLevel == 2 && intPlayerX < 600) {

      intLevel += 1;

    }

  }

  /**
   * player interactions with objects 
   */
  public void playerInteractions() {

    if (blnInteract == true) {

      if (intLevel == 2) {

        if (intPlayerY > height / 2) {

          if (get(intPlayerX, intPlayerY - 8) == -16776961) {

          // desk pop up 
          // image();

          }

        } else {

          if (get(intPlayerX, intPlayerY + 64) == -16776961 || get(intPlayerX - 8, intPlayerY) == -16776961 || get(intPlayerX, intPlayerY - 8) == -16776961 ) {

          // safe pop up 
          // image();

          }

        }
      } else if (intLevel == 6) {

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
  public void NextLevel() {

    if (intLevel == 4) {

    } else if (intLevel == 6) {

      if (strPassword.equals("fabroa") == true) {

        blnNextLevel = true;

      }
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
  }

  public boolean blnMoving() {
    return blnUp || blnDown || blnLeft || blnRight;
  }
}
