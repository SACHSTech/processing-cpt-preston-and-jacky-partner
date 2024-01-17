import processing.core.PApplet;
import processing.core.PImage;
import java.lang.reflect.Array;
import java.util.Arrays;
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

  // level 2 and 3 variables 
  PImage[] imgPage;
  PImage imgSafe;
  boolean blnVerify = false;
  boolean blnPage, blnSafe = false;
  int intPageNumber = 0;
  String strCode = "";

  // level 5 variable 
  String strPassword = "";

  // level 7 variables
  boolean blnRickPoster, blnGundamPoster, blnIPoster, blnKeyI, blnTrapDoor, blnLockedTrapDoor = false;

  // level 9 variables
  PImage[] imgCards;
  int[] intCardLocations = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
  int[] intCardStatus = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
  boolean[] blnFound = {false,false,false,false,false,false,false,false};
  int[] intX = {264,307,350,393,264,307,350,393,264,307,350,393,264,307,350,393};
  int[] intY = {249,249,249,249,302,302,302,302,355,355,355,355,408,408,408,408};
  boolean blnTable = false;
  boolean blnFirstTimeEntered = true;
  int intCardsFlipped = 0;
  Random intRand = new Random();

  // player direction
  String strDirection = "Down";

  // game starting and ending variables
  boolean blnGameStarting = true;
  boolean blnGameEnding = false;

  // game oxygen meter variables
  boolean blnOxygenMeter = false;
  int intOxygenMeter = 100;
  int intTotalOxygen;

  // level variables 
  boolean[] blnNextLevel = {true,false,false,false,false,false};
  int intNumLevels = 10;
  int intLevel = 8;

  // number of frames for each player animation 
  int intNumFrames = 4;
  int intMoveFrames = 0;

  // player position 
  int intPlayerX = 300;
  int intPlayerY = 300;

  // player booleans 
  boolean blnUp, blnDown, blnLeft, blnRight, blnInteract, blnLeftArrow, blnRightArrow;

  /**
   * sets the size of the screen for the game
   */
  public void settings() {
    
    size(672, 672);

  }

  /** 
   * loads all images to be drawn for the game
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

    // settiing up image variables for pages
    imgPage = new PImage[2];

    // setting up image variables for cards
    imgCards = new PImage[17];

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

    for (int i = 0; i <= 16; i++) {

      imgCards[i] = loadImage("escape_room/popups/card" + i + ".png");

    }

    imgSafe = loadImage("escape_room/popups/safe.png");

  }

  /**
   * draws the methods that make up the game
   */
  public void draw() {

    if (blnGameStarting == true && intOxygenMeter > 0) {
     
      startingScreen();
      drawCollisionMaps();
      playerMovementAndCollisions();
      playerInteractions();
      drawMaps();
      oxygenMeter();
      playerUpdate();
      drawPopUps();
      nextLevel();

    // draws a screen if the player has completed the game without running out of oxygen 
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
      text("mission failed sucessfully, we'll get'em next time", width / 2, height / 2);

    }
  }

  /**
   * draws a starting screen for the game and will allow the player to decide difficulty before starting 
   */
  public void startingScreen() {
  
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

    } else if (intLevel == 7 && blnNextLevel[3] == false) {

      fill(0);
      rect(0,0,8,height);

    }
  }

  /**
   * draws the oxygen meter and keeps track if the player has oxygen left for the rest of the game
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

    // prevents players from moving if they are interacting with an object
    if (blnPage == false && blnSafe == false && blnRickPoster == false && blnGundamPoster == false && blnIPoster == false)  {
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

    // detects if the player is trying to interact with an object, everything inside this if statement runs when that key is pressed
    if (blnInteract == true) {

      if (intLevel == 3) {

        if (intPlayerY < height / 2) {

          // detection for the desk
          if (get(intPlayerX, intPlayerY - 8) == -16776961 || get(intPlayerX + 64, intPlayerY) == -16776961) {

              if (blnPage == true) {
  
                blnPage = false;
                delay(300);
  
              // allows the player to leave the desk and page pop up if they think they have figured out the correct code for the room 
              } else if (blnPage == false) {
  
                blnPage = true;
                delay(300);
  
              }

          }

        } else {

          // detection for the safe in the room 
          if (get(intPlayerX, intPlayerY + 64) == -16776961 || get(intPlayerX - 8, intPlayerY) == -16776961 || get(intPlayerX, intPlayerY - 8) == -16776961 ) {

            // sees if the player is interacting with the safe
            if (blnSafe == true) {
  
                blnSafe = false;
                delay(300);
  
            // allows the player to exit the safe pop up if they don't know the correct code 
            } else if (blnSafe == false) {

                blnSafe = true;
                delay(300);
  
            }
          }
        }

      // note, the quote we are using is "good morning everyone, please take your seats"
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

        if (intPlayerY < 300) {

          // detection for the Rick Poster
          if ((intPlayerX > 50 && intPlayerX < 156) && (get(intPlayerX, intPlayerY - 8) == -16776961)) {

            if (blnRickPoster == false) {

              blnRickPoster = true;
              delay(300);

            } else if (blnRickPoster == true) {

              blnRickPoster = false;
              delay(300);

            }

          // detection for the Gundam Poster 
          } else if ((intPlayerX > 170 && intPlayerX < 240) && (get(intPlayerX,intPlayerY - 16) == -16776961)) {

            if (blnGundamPoster == false) {

              blnGundamPoster = true;
              delay(300);


            } else if (blnGundamPoster == true) {

              blnGundamPoster = false;
              delay(300);

            }

          // detection for the Robotic Poster
          } else if ((intPlayerX > 300 && intPlayerX < 380) && (get(intPlayerX,intPlayerY - 8) == -16776961)) {

          if (blnIPoster == false) {

            blnIPoster = true;
            delay(300);
          
          } else if (blnIPoster == true) {

            blnIPoster = false;
            delay(300);

          } 

          } 

        // detection for the shelf that is in the room
        } else if (get(intPlayerX, intPlayerY + 64) == -16776961 || get(intPlayerX + 42, intPlayerY + 64) == - 16776961){
          
          blnKeyI = true;

        // detects if the player is trying to interact with the trap door before doing all the needed steps before it 
        }  else if (blnTrapDoor == false && (get(intPlayerX, intPlayerY + 56) == -256 || get(intPlayerX + 42, intPlayerY + 56) == -256)) {

          blnLockedTrapDoor = true; 
            
        // detects if the player is trying to interact with the trap door after performing all the needed tasks, this will also allow the player to move down a floor in that room 
        } else if (blnTrapDoor == true && (get(intPlayerX, intPlayerY + 56) == -256 || get(intPlayerX + 42, intPlayerY + 56) == -256)) {

          intLevel += 1;
          blnFirstTimeEntered = true;

        }  

      // allows the player to go back through the trap door to the uppper floor 
      } else if (intLevel == 8) {

        if (get(intPlayerX, intPlayerY + 56) == -256 || get(intPlayerX + 42, intPlayerY + 56) == -256) {
          
          intLevel -= 1;

        }

        // detects if the player is interacting with the table 
        if ((get(intPlayerX,intPlayerY + 56) == -16776961) || (get(intPlayerX + 42,intPlayerY + 56) == -16776961) || (get(intPlayerX + 42, intPlayerY) == -16776961) || (get(intPlayerX,intPlayerY) == -16776961)) {

          if (blnTable == true) {

            blnTable = false;
            delay(300);

          } else if (blnTable == false) {

            blnTable = true;
            delay(300);

          }

        }

      } 
    } 
    
    // passively detects if the player is still standing on the trap door even though they can't open it. Does not require the player to hit any keys 
    if (intLevel == 7 &&  (get(intPlayerX, intPlayerY + 64) != -256 || get(intPlayerX + 42, intPlayerY + 64) != -256)) {

      blnLockedTrapDoor = false;

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
   * draws the needed pop ups for the map 
   */
  public void drawPopUps() {

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
            text(strCode, 110, 215);
   

          } else {

            // changes the variable to WRONG to let the user know they got the code wrong and must try again 
            strCode = "WRONG";
            blnVerify = false;
            fill(173, 216, 230);       
            textSize(130);
            text(strCode, 110, 215);
          
          } 

        } else {

          // displays the numbers that the user inputted into the safe
          fill(173, 216, 230);       
          textSize(130);
          text(strCode, 110, 220);
          
          // if the numbers were wrong, it will show wrong for .5 seconds and then set the code to empty to allow them to try again 
          if (strCode.equals("WRONG")) {

            delay(500);
            strCode = "";

          // if the numbers were right, it show open for .5 seconds and then it will open the safe door, which will remove the pop keypad for the safe
          } else if (strCode.equals("OPEN")) {

            delay(500);
            strCode = "OPEN ";
            blnSafe = false;

          // allows the player to sill use the safe even if they have unlocked it 
          } else if (strCode.equals("OPEN "));

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
      
    } else if (intLevel == 7) {

      // pops up text when the player interacts with the Rick Poster
      if (blnRickPoster == true) {

        fill(255);
        textSize(20);
        text("imagine getting rick rolled", 215, 500);
        

      // pops up text when the player interacts with the gundam poster 
      } else if (blnGundamPoster == true) {

        fill(255);
        textSize(20);
        text("looks like the creator was a gundam fan",150, 500);
           

      // pops text once the player interacts with the robotic like poster 
      } else if (blnIPoster == true) {

        fill(255);
        textSize(20);
        text("You see a bent corner and decide", 150, 500);
        text("to pull on it revealing a I shaped hole in the wall", 100, 525);

        // gives different more unique text if the player has found the specific key before ineracting with it 
        if (blnKeyI == true) {

          text("You put in the ''I'' shaped key that you found.", 120, 550);
          text("Clank, Whirrr, Hummmm, something must of happened", 75, 575);
          blnTrapDoor = true;

        }

      // gives a hint to the player if they are trying to interact with the trap door before performing all the needed steps before it 
      } else if (blnLockedTrapDoor == true) {

        text("You pull with all your might, but it seems to be sealed tight",75,500);

      }
    } else if (intLevel == 8) {

      // randomizes the cards the first time the player walks in, every other time, the cards don't randomize
      if (blnFirstTimeEntered == true && blnTable == false) {

        for (int i = 0; i < intCardLocations.length; i++) {

          int randomIndexToSwap = intRand.nextInt(intCardLocations.length);
          int temp = intCardLocations[randomIndexToSwap];
          intCardLocations[randomIndexToSwap] = intCardLocations[i];
          intCardLocations[i] = temp;

        }
      } 

      // checks if the player is interacting with the table 
      if (blnTable == true) {  
        
        // draws a grid of cards that will lie on the table
        for (int i = 0; i < 16; i++) {

          // draws the back of the cards so that the player does not know which is which
          if (intCardStatus[intCardLocations[i]] == 0)  {

            image(imgCards[16],intX[i],intY[i]);
            
          // if a card has been clicked, it will reveal what is on the other side of it
          } else if (intCardStatus[intCardLocations[i]] == 1) {

            image(imgCards[intCardLocations[i]],intX[i],intY[i]);

          } 
        }

        // checks if two cards have been flipped, and then checks if any pairs have been found
        if (intCardsFlipped >= 2) {

          // if a pair has been found, it removes them from the grid 
          if (intCardStatus[0] == 1 && intCardStatus[1] == 1) {

            intCardStatus[0] = 2;
            intCardStatus[1] = 2;
            blnFound[0] = true;
            intCardsFlipped = 0;;

          } else if (blnFound[0] == false) {

            intCardStatus[0] = 0;
            intCardStatus[1] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[2] == 1 && intCardStatus[3] == 1) {

            intCardStatus[2] = 2;
            intCardStatus[3] = 2;
            blnFound[1] = true;
            intCardsFlipped = 0;

          } else if (blnFound[1] == false){

            intCardStatus[2] = 0;
            intCardStatus[3] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[4] == 1 && intCardStatus[5] == 1) {

            intCardStatus[4] = 2;
            intCardStatus[5] = 2;
            blnFound[2] = true;
            intCardsFlipped = 0;

          } else if (blnFound[2] == false) {

            intCardStatus[4] = 0;
            intCardStatus[5] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[6] == 1 && intCardStatus[7] == 1) {

            intCardStatus[6] = 2;
            intCardStatus[7] = 2;
            blnFound[3] = true;
            intCardsFlipped = 0;

          } else if (blnFound[3] == false) {

            intCardStatus[6] = 0;
            intCardStatus[7] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[8] == 1 && intCardStatus[9] == 1) {

            intCardStatus[8] = 2;
            intCardStatus[9] = 2;
            blnFound[4] = true;
            intCardsFlipped = 0;

          } else if (blnFound[4] == false) {

            intCardStatus[8] = 0;
            intCardStatus[9] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[10] == 1 && intCardStatus[11] == 1) {

            intCardStatus[10] = 2;
            intCardStatus[11]= 2;
            blnFound[5] = true;
            intCardsFlipped = 0;

          } else if (blnFound[5] == false) {

            intCardStatus[10] = 0;
            intCardStatus[11] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[12] == 1 && intCardStatus[13] == 1) {

            intCardStatus[12] = 2;
            intCardStatus[13] = 2;
            blnFound[6] = true;
            intCardsFlipped = 0;

          } else if (blnFound[6] == false) {

            intCardStatus[12] = 0;
            intCardStatus[13] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[14] == 1 && intCardStatus[15] == 1) {

            intCardStatus[14] = 2;
            intCardStatus[15] = 2;
            blnFound[7] = true;
            intCardsFlipped = 0;

          } else if (blnFound[7] == false) {

            intCardStatus[14] = 0;
            intCardStatus[15] = 0;
            intCardsFlipped = 0;

          }
        }
      }
    }
  }

  /**
   * detects if the player has completed a level and changes the level map accordingly 
   */
  public void nextLevel() {
    // allows players to go to the next level once they have completed it
    if ((intLevel == 0 | intLevel == 1) && intPlayerX < 16) {

      intLevel += 1;
      intPlayerX = 664;

    // checks to see if the player is proceeding to the next level and if they are able to, if they are it will change the map and change their location as well
    } else if (intLevel == 2 && intPlayerX < 600) {

      intLevel += 1;

    // detects if the player is trying to move onto the next level, and if the player has met all the requirements to do so, it increases the level by 1 
    } else if (intPlayerX <= 16 && intLevel == 3 && blnNextLevel[1] == true) {

      intLevel += 1;
      intPlayerX = 664;
      
    } else if (intLevel == 4 && intPlayerX < 16) {

      intLevel += 1;
      intPlayerX = 664;

    } else if (intLevel == 5 && intPlayerY <= 16 && blnNextLevel[2] == true) {

      intLevel += 1;
      intPlayerY = 664;

    } else if (intLevel == 6 && intPlayerY < 16) {

      intLevel += 1;
      intPlayerY = 664;

    } else if (intLevel == 7 && intPlayerX < 16 && blnNextLevel[3] == true) {

      intLevel += 2;
      intPlayerX = 664;

    } else if (intLevel == 10 && intPlayerY > 664 && blnNextLevel[4] == true) {

      intLevel += 1;
      intPlayerY = 16;

    }

    // detects if players are trying to leave that level, and it will move them down a level if they want to 
     if ((intLevel == 1 || intLevel == 0) && intPlayerX > 664) {

      intLevel -= 1;
      intPlayerX = 16;

    } else if (intLevel == 4 && intPlayerX > 664) {
      
      intLevel -= 1;
      intPlayerX = 16;

    } else if (intLevel == 5 && intPlayerX > 664) {

      intLevel -= 1;
      intPlayerX = 16;

    } else if (intLevel == 6 && intPlayerY > 664) {

      intLevel -= 1;
      intPlayerY = 16;

    } else if (intLevel == 9 && intPlayerX > 664) {

      // goes down 2 levels becuaes the player enters into the top floor of that room
      intLevel -= 2;
      intPlayerX = 16;

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
   * detects moues inputs and outputs it 
   */
  public void mousePressed() {

    if (blnSafe == true) {

      // deletion key is placed here because it needs to run even when the code has hit its max length 
      if ((mouseX > 261 && mouseX < 335) && (mouseY > 506 && mouseY < 592)) {

        if (strCode.length() > 0) {

          strCode = strCode.substring(0, strCode.length() - 1);

        }
      }

      // verification key is placed here becuaes it needs to run even when the code has hit its max length 
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
    } else if (blnTable == true) {

      if (mouseY > 249 && mouseY < 297) {

        if (mouseX > 258 && mouseX < 300) {

          intCardStatus[intCardLocations[0]] += 1;  
          intCardsFlipped += 1;          

        } else if (mouseX > 305 && mouseX < 343) {

          intCardStatus[intCardLocations[1]] += 1;
          intCardsFlipped += 1; 

        } else if (mouseX > 344 && mouseX < 386) {

          intCardStatus[intCardLocations[2]] += 1;
          intCardsFlipped += 1; 

        } else if (mouseX > 387 && mouseX < 425) {

          intCardStatus[intCardLocations[3]] += 1;
          intCardsFlipped += 1; 
          
        }

      } else if (mouseY > 300 && mouseY < 348) {

        if (mouseX > 258 && mouseX < 300) {

          intCardStatus[intCardLocations[4]] += 1;   
          intCardsFlipped += 1;          

        } else if (mouseX > 305 && mouseX < 343) {

          intCardStatus[intCardLocations[5]] += 1;
          intCardsFlipped += 1; 

        } else if (mouseX > 344 && mouseX < 386) {

          intCardStatus[intCardLocations[6]] += 1;
          intCardsFlipped += 1; 

        } else if (mouseX > 387 && mouseX < 425) {

          intCardStatus[intCardLocations[7]] += 1;
          intCardsFlipped += 1; 
          
        }

      } else if (mouseY > 351 && mouseY < 399) {

        if (mouseX > 258 && mouseX < 300) {

          intCardStatus[intCardLocations[8]] += 1; 
          intCardsFlipped += 1;            

        } else if (mouseX > 305 && mouseX < 343) {

          intCardStatus[intCardLocations[9]] += 1;
          intCardsFlipped += 1; 

        } else if (mouseX > 344 && mouseX < 386) {

          intCardStatus[intCardLocations[10]] += 1;
          intCardsFlipped += 1; 

        } else if (mouseX > 387 && mouseX < 425) {

          intCardStatus[intCardLocations[11]] += 1;
          intCardsFlipped += 1; 
          
        }

      } else if (mouseY > 405 && mouseY < 453) {

        if (mouseX > 258 && mouseX < 300) {

          intCardStatus[intCardLocations[12]] += 1;  
          intCardsFlipped += 1;           

        } else if (mouseX > 305 && mouseX < 343) {

          intCardStatus[intCardLocations[13]] += 1;
          intCardsFlipped += 1; 

        } else if (mouseX > 344 && mouseX < 386) {

          intCardStatus[intCardLocations[14]] += 1;
          intCardsFlipped += 1; 

        } else if (mouseX > 387 && mouseX < 425) {

          intCardStatus[intCardLocations[15]] += 1;
          intCardsFlipped += 1; 
          
        }
      }
    }
  }

  /**
   * sees which direction the player is currently moving in 
   * @return a boolean value as true that corresponds to the last direction that player as moving in 
   */
  public boolean blnMoving() {
    return blnUp || blnDown || blnLeft || blnRight;
  }
}
