import processing.core.PApplet;
import processing.core.PImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class escape_room extends PApplet {

  // level image variable 
  PImage[] imgLevel;
  PImage[] imgLevelCollision;

  // keycard image variable
  PImage[] imgKeyCard;
  int[] intKeyCardTimer = {0,0,0,0};

  // player image movement variables 
  PImage[] imgPlayerLeft;
  PImage[] imgPlayerRight;
  PImage[] imgPlayerUp;
  PImage[] imgPlayerDown;

  // starting screen variables
  PImage imgStartingScreen;
  boolean blnStartButtonPressed = false;
  boolean blnEasy = false;
  boolean blnMedium = false;
  boolean blnHard = false;

  // level 2 and 3 variables 
  PImage[] imgPage;
  PImage imgSafe;
  boolean blnVerify = false;
  boolean blnPage, blnSafe = false;
  int intPageNumber = 0;
  String strCode = "";

  // level 5 variable 
  String strPassword = "";
  boolean blnDesk = false;
  int intDeskTimer = 0;

  // level 7 variables                            
  boolean blnRickPoster, blnGundamPoster, blnIPoster, blnKeyI, blnTrapDoor, blnLockedTrapDoor = false;

  // level 8 variables
  PImage[] imgCards;
  PImage imgCrowBar;
  PImage imgStairs;
  PImage imgIKey;
  boolean[] blnFound = {false,false,false,false,false,false,false,false};
  boolean blnCrowBar,blnStairs, blnTable, blnFirstTimeEntered, blnBox = false;
  int[] intCardLocations = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
  int[] intCardStatus = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
  int[] intX = {264,307,350,393,264,307,350,393,264,307,350,393,264,307,350,393};
  int[] intY = {249,249,249,249,302,302,302,302,355,355,355,355,408,408,408,408};
  int intCardsFlipped = 0;
  int intCardDelay = 0;
  int intKeyTimer = 0;
  int intEasterEggDelay = 0;
  int intCrowBarTimer = 0;
  Random intRand = new Random();

  // player direction
  String strDirection = "Down";

  // game starting and ending variables
  boolean blnGameStarting = false;
  boolean blnGameEnding = false;

  // game oxygen meter variables
  boolean blnOxygenMeter = false;
  int intOxygenMeter = 1;
  int intTotalOxygen = 1;

  // level variables 
  boolean[] blnNextLevel = {true,false,false,false,false,false};
  boolean[] blnLeftLevel = {false,false,false,false};
  int intNumLevels = 10;
  int intLevel = 0;

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

    imgKeyCard = new PImage[4];

    // loading in all level images
    for (int i = 0; i < intNumLevels; i++) {

      imgLevel[i] = loadImage("escape_room/levels/level" + i + ".png"); 

    }

    // loading in all level collision maps
    for (int i = 0; i < intNumLevels; i++) {

      imgLevelCollision[i] = loadImage("escape_room/levels/collisions/level" + i + ".png"); 

    }

    // loading in all left player animation images
    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerLeft[i] = loadImage("escape_room/player/playerLeft" + i + ".png");
      imgPlayerLeft[i].resize(42,54);

    }
  
    // loading in all right player animation images
    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerRight[i] = loadImage("escape_room/player/playerRight" + i + ".png");
      imgPlayerRight[i].resize(42,54);

    }
      
    // loading in all up player animation images
    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerUp[i] = loadImage("escape_room/player/playerUp" + i + ".png");
      imgPlayerUp[i].resize(42,54);

    }
  
    // loading in all down player animation images
    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerDown[i] = loadImage("escape_room/player/playerDown" + i + ".png");
      imgPlayerDown[i].resize(42,54);

    }

    // loading page 1 of the stack of papers found on level 3
    imgPage[0] = loadImage("escape_room/popups/page" + 0 + ".png");

    // loading in page 2 of the stack of papers found on level 3
    imgPage[1] = loadImage("escape_room/popups/page" + 1 + ".png");

    // loading in safe pop up for the safe in level 3
    imgSafe = loadImage("escape_room/popups/safe.png");

    // loading in cards for the level 8 matching card game
    for (int i = 0; i <= 16; i++) {

      imgCards[i] = loadImage("escape_room/popups/card" + i + ".png");

    }

    // loading and resized the crowbar image found on level 8
    imgCrowBar = loadImage("escape_room/popups/crowbar.png");
    imgCrowBar.resize(60,72);

    // loading in the stairs image found on level 8
    imgStairs = loadImage("escape_room/popups/stairs.png");

    // loading in the starting screen image 
    imgStartingScreen = loadImage("escape_room/startScreen.png");

    // loading in the keycard images
    for (int i = 0; i < 4; i++) {

      imgKeyCard[i] = loadImage("escape_room/popups/keycard" + i + ".png");

    }

    // loading in the I shaped key
    imgIKey = loadImage("escape_room/popups/iKey.png");

  }

  /**
   * draws the methods that make up the game
   */
  public void draw() {

    // detects if the game has started 
    if (blnGameStarting == false) {

      startingScreen();

    // detects if the game has started and if the user still has oxygen left 
    } else if (blnGameStarting == true && intOxygenMeter > 0 ) {
     
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
      textSize(20);
      fill(255);
      text("mission failed sucessfully, we'll get'em next time", width / 2, height / 2);

    }
  }

  /**
   * draws a starting screen for the game and will allow the player to decide difficulty before starting 
   */
  public void startingScreen() {

    // detects if the game has started
    if (blnGameStarting == false) {

      // draws the starting screen image
      image(imgStartingScreen,CENTER,CENTER);

      // changes the text colour of the exit button if they are hovering over the button
      if ((mouseX > 20 && mouseX < 75) && (mouseY > 10 && mouseY < 35)) {

        fill(255);
        stroke(0);
        rect(20,10,55,25);
        fill(100,0,0);
        strokeWeight(5);
        textSize(20);
        text("EXIT",25,30);

      } else {

        fill(255);
        stroke(0);
        rect(20,10,55,25);
        fill(0);
        strokeWeight(5);
        textSize(20);
        text("EXIT",25,30);

      }

      // detects if the start button has been pressed 
      if (blnStartButtonPressed == false) {  

        // detects if the player is hovering over the button, and changes the text colour accordingly 
        if ((mouseX > 240 && mouseX < 445) && (mouseY > 290 && mouseY < 365)) {

          fill(255);
          stroke(0,0,100);
          rect(240,290,205,75);
          fill(0,0,100);
          strokeWeight(5);
          textSize(60);
          text("START",250,350);

        } else {

          fill(255);
          stroke(0);
          rect(240,290,205,75);
          fill(0);
          strokeWeight(5);
          textSize(60);
          text("START",250,350);

        }

      } else if (blnStartButtonPressed == true) {
        
        // detects if the user has selected the easy difficulty 
        if ((mouseX > 250 && mouseX < 445) && (mouseY > 200 && mouseY < 275)) {

          fill(255);
          stroke(0,0,100);
          rect(250,200,195,75);
          fill(0,0,100);
          strokeWeight(5);
          textSize(60);
          text("EASY",275,260);

        } else {

          fill(255);
          stroke(0);
          rect(250,200,195,75);
          fill(0);
          strokeWeight(5);
          textSize(60);
          text("EASY",275,260);

        }

        // detets if the player has selected the medium difficulty 
        if ((mouseX > 220 && mouseX < 470) && (mouseY > 300 && mouseY < 375)) {

          fill(255);
          stroke(0,0,100);
          rect(220,300,250,75);
          fill(0,0,100);
          strokeWeight(5);
          textSize(60);
          text("MEDIUM",225,360);

        } else {

          fill(255);
          stroke(0);
          rect(220,300,250,75);
          fill(0);
          strokeWeight(5);
          textSize(60);
          text("MEDIUM",225,360);

        }

        // detects if the player has selected the hard difficulty 
        if ((mouseX > 250 && mouseX < 445) && (mouseY > 400 && mouseY < 475)) {

          fill(255);
          stroke(0,0,100);
          rect(250,400,195,75);
          fill(0,0,100);
          strokeWeight(5);
          textSize(60);
          text("HARD",265,460);

        } else {

          fill(255);
          stroke(0);
          rect(250,400,195,75);
          fill(0);
          strokeWeight(5);
          textSize(60);
          text("HARD",265,460);

        }
      }
    } 
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

    // sets the amount of oxygen higher if the player has selected the medium difficulty and lower if they selected the hard difficulty 
    if (blnMedium == true) {

      intOxygenMeter = 300;
      intTotalOxygen = 300;
      blnMedium = false;

    } else if (blnHard == true) {

      intOxygenMeter = 200;
      intTotalOxygen = 200;
      blnHard = false;

    }

    // only starts the meter once they have left the tutorial level and if the player did not select the easy difficulty 
    if (blnOxygenMeter == true && blnEasy != true) {

      fill(173, 216, 230);
      noStroke();
      rect(640,640,20, -intOxygenMeter);
      strokeWeight(1);
      stroke(0,0,0);
      noFill();
      rect(640,640,20, -intTotalOxygen);
    
      // slowly ticks away at the oxygen meter 
      if (frameCount % 120 == 0) {

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
    if (blnPage == false && blnSafe == false && blnRickPoster == false && blnGundamPoster == false && blnIPoster == false && blnTable == false)  {
      
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

        // detects if the player is interacting with the table 
        if ((get(intPlayerX, intPlayerY + 64) == -16776961 || get(intPlayerX - 8, intPlayerY) == -16776961 || get(intPlayerX, intPlayerY - 8) == -16776961)) {

          blnDesk = true;

          // sets the timer back to 0 everyoen the player interacts with the desk so that they can see the hint on the screen again
          intDeskTimer = 0;

        } 
        // runs even when the password is at max length 
        if ((intPlayerY > 261 && intPlayerY <= 375) && (intPlayerX > 237 && intPlayerX < 380) && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) { 
            
          // prevents the player from using the delete key even when there is nothing left in the string 
          if (strPassword.length() > 0) {

            strPassword = strPassword.substring(0, strPassword.length() - 1);

          }

          delay(300);

        }

        if (strPassword.length() < 8) {
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

            // coordinates for the letter H
            } else if (intPlayerX > 380 && intPlayerX < 503 && (get(intPlayerX, intPlayerY + 56) == -3584 || get(intPlayerX + 30, intPlayerY + 56) == -3584)) {

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
          delay(300);
          blnFirstTimeEntered = true;

        }  

      // allows the player to go back through the trap door to the uppper floor 
      } else if (intLevel == 8) {

        if (get(intPlayerX, intPlayerY + 56) == -256 || get(intPlayerX + 42, intPlayerY + 56) == -256) {
          
          intLevel -= 1;
          delay(300);

        }

        // detects if the player is interacting with the table 
        if ((get(intPlayerX,intPlayerY + 56) == -16776961) || (get(intPlayerX + 42,intPlayerY + 56) == -16776961) || (get(intPlayerX + 42, intPlayerY) == -16776961) || (get(intPlayerX,intPlayerY) == -16776961)) {

          if (intPlayerX < (width / 2) && intPlayerY > (height / 2)) {
            
            if (blnTable == true) {

              blnTable = false;
              delay(300);

            } else if (blnTable == false) {

              blnTable = true;
              delay(300);

            }

      
          }  
          
          // box detection
          if (intPlayerX < (width / 2) && intPlayerY < (height / 2)) {

            // the player can only see what is inside the boxes once they've picked up a crowbar
            if (blnCrowBar == true) {

              blnBox = true;

            }

          // stairs detection, prevents the player from walking throuhg it, while allowing them to walk behind it
          } else if (intPlayerX < (width / 2) && intPlayerY > (height / 2)) {

  

          }

        // crowbar detection
        } else if ((get(intPlayerX,intPlayerY + 56) == -16711936) || (get(intPlayerX + 42,intPlayerY + 56) == -16711936) || (get(intPlayerX + 42, intPlayerY) == -16711936) || (get(intPlayerX,intPlayerY) == -16711936)) {

          blnCrowBar = true;

        } 
      } 
    } 
    
    // passively detects if the player is still standing on the trap door even though they can't open it. Does not require the player to hit any keys 
    if (intLevel == 7 && (get(intPlayerX, intPlayerY + 56) != -256 && get(intPlayerX + 42, intPlayerY + 56) != -256)) {

      blnLockedTrapDoor = false;

    } 
    
    // passively detects if the player is walking behind the ladder and will print an image over the player if they are 
    if (intLevel == 8 && (get(intPlayerX,intPlayerY + 56) == -16711936) || (get(intPlayerX + 42,intPlayerY + 56) == -16711936) || (get(intPlayerX + 42, intPlayerY) == -16711936) || (get(intPlayerX,intPlayerY) == -16711936)) {

      blnStairs = true;

    } else if (intLevel == 8 && (get(intPlayerX,intPlayerY + 56) != -16711936) && (get(intPlayerX + 42,intPlayerY + 56) != -16711936) && (get(intPlayerX + 42, intPlayerY) != -16711936) && (get(intPlayerX,intPlayerY) != -16711936)) {

      blnStairs = false;

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

      if (blnNextLevel[1] == true && intKeyCardTimer[0] <= 100 && blnLeftLevel[0] == false) {

        image(imgKeyCard[0],intPlayerX,intPlayerY - 30);
        intKeyCardTimer[0] ++;

      } else if (blnNextLevel[1] == true && blnLeftLevel[0] == true) {

        image(imgKeyCard[0],435,200);

      }

    } else if (intLevel == 5) {

      // checks if the password that is put in is the correct one and will allow the player to move onto the next level 
      if (strPassword.equals("fabroa") == true) {

        blnNextLevel[2] = true;

      } else {

        blnNextLevel[2] = false;

      }

      // intDeskTimer is used to display the text but then remove it from the screen to allow the user to see what they are typing
      if (blnDesk == true && intDeskTimer < 150) {

        // prints out the clue for the room onto the wall
        fill(255);
        textSize(20);
        text("Good morning class,",65, 95);
        text("everyone please sit",65, 115);
        text("down. By Mr. ",65, 135);
        
        // used to remove the text from the screen after a set time as gone by
        intDeskTimer ++;

      } else {

        // displays what the user has tyed onto the wall, placed in the else statement so that the hint and this don't get printed at the same time
        fill(255);
        textSize(40);
        text(strPassword,65, 125);

      }

      // prints out the keycard above the player's head for a short duration
      if (blnNextLevel[2] == true && intKeyCardTimer[1] <= 100 && blnLeftLevel[1] == false) {

        image(imgKeyCard[1],intPlayerX,intPlayerY - 30);
        intKeyCardTimer[1] ++;

      // prints out the keycard onto the desk after the player has left the room and has walked back in 
      } else if (blnNextLevel[2] == true && blnLeftLevel[1] == true) {

        image(imgKeyCard[1], 50,555);

      }
      
    } else if (intLevel == 7) {

      // prints out the keycard once the player has completed the level and left it 
      if (blnNextLevel[3] == true && blnLeftLevel[2] == true) {

        image(imgKeyCard[2],75,560);

      }

      // prints out a key over the player's head
      if (blnKeyI == true && intKeyTimer <= 100) {

        image(imgIKey,intPlayerX,intPlayerY - 30);
        intKeyTimer ++;

      }

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
          
          textSize(20);
          text("You put in the ''I'' shaped key that you found.", 120, 550);
          text("Clank, Whirrr, Hummmm, something must of happened", 75, 575);
          blnTrapDoor = true;

        }

      // gives a hint to the player if they are trying to interact with the trap door before performing all the needed steps before it 
      } else if (blnLockedTrapDoor == true) {

        fill(255);
        textSize(20);
        text("You pull with all your might,",208,500);
        text("but it seems to be sealed tight",200,525);

      }
    } else if (intLevel == 8) {

      // pritns out the keycard once the level is completed
      if (blnNextLevel[3] == true && intKeyCardTimer[2] <= 100 && blnLeftLevel[2] == false) {

        image(imgKeyCard[2],intPlayerX,intPlayerY - 30);
        intKeyCardTimer[2] ++;

      }

      // randomizes the cards the first time the player walks in, every other time, the cards don't randomize
      if (blnFirstTimeEntered == true && blnTable == false) {

        for (int i = 0; i < intCardLocations.length; i++) {

          int intRandomSwap = intRand.nextInt(intCardLocations.length);
          int intTemp = intCardLocations[intRandomSwap];
          intCardLocations[intRandomSwap] = intCardLocations[i];
          intCardLocations[i] = intTemp;

        }
      } 

      // prints out an image of the stairs to cover the player as they it 
      if (blnStairs == true) {

        image(imgStairs,550,353);

      }

      // checks to see if all the needed actions have been performed before reavling an easter egg, there is a tiemr variable so the easter egg is removed after a set duration
      if (blnCrowBar == true && blnBox == true && intEasterEggDelay < 60) {
        
        
        intEasterEggDelay ++;

      }

      // detects if the user has the crowbar and then prints out the crow bar above the player's head 
      if (blnCrowBar == true && intCrowBarTimer <= 60) {

        image(imgCrowBar,intPlayerX,intPlayerY - 60);
        intCrowBarTimer ++;

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

        // detects if the player has completed teh card game
        if ((blnFound[0] && blnFound[1] && blnFound[2] && blnFound[3] && blnFound[4] && blnFound[5] && blnFound[6] && blnFound[7]) == true) {

          blnTable = false;
          blnNextLevel[3] = true;

        }

        // checks if two cards have been flipped, and then checks if any pairs have been found
        if (intCardsFlipped >= 2) {

          intCardDelay += 1;

          // if a pair has been found, it removes them from the grid 
          if (intCardStatus[0] == 1 && intCardStatus[1] == 1 && intCardDelay >= 30) {
          
            intCardStatus[0] = 2;
            intCardStatus[1] = 2;
            blnFound[0] = true;
            intCardsFlipped = 0;

          } else if (blnFound[0] == false && intCardDelay >= 30) {

            intCardStatus[0] = 0;
            intCardStatus[1] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[2] == 1 && intCardStatus[3] == 1 && intCardDelay >= 30) {

            intCardStatus[2] = 2;
            intCardStatus[3] = 2;
            blnFound[1] = true;
            intCardsFlipped = 0;

          } else if (blnFound[1] == false && intCardDelay >= 30){

            intCardStatus[2] = 0;
            intCardStatus[3] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[4] == 1 && intCardStatus[5] == 1 && intCardDelay >= 30) {

            intCardStatus[4] = 2;
            intCardStatus[5] = 2;
            blnFound[2] = true;
            intCardsFlipped = 0;

          } else if (blnFound[2] == false && intCardDelay >= 30)  {

            intCardStatus[4] = 0;
            intCardStatus[5] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[6] == 1 && intCardStatus[7] == 1 && intCardDelay >= 30) {

            intCardStatus[6] = 2;
            intCardStatus[7] = 2;
            blnFound[3] = true;
            intCardsFlipped = 0;

          } else if (blnFound[3] == false && intCardDelay >= 30) {

            intCardStatus[6] = 0;
            intCardStatus[7] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[8] == 1 && intCardStatus[9] == 1 && intCardDelay >= 30) {

            intCardStatus[8] = 2;
            intCardStatus[9] = 2;
            blnFound[4] = true;
            intCardsFlipped = 0;

          } else if (blnFound[4] == false && intCardDelay >= 30) {

            intCardStatus[8] = 0;
            intCardStatus[9] = 0;
            intCardsFlipped = 0;
   
          }
          
          if (intCardStatus[10] == 1 && intCardStatus[11] == 1 && intCardDelay >= 30) {

            intCardStatus[10] = 2;
            intCardStatus[11]= 2;
            blnFound[5] = true;
            intCardsFlipped = 0;
      

          } else if (blnFound[5] == false && intCardDelay >= 30) {

            intCardStatus[10] = 0;
            intCardStatus[11] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[12] == 1 && intCardStatus[13] == 1 && intCardDelay >= 30) {

            intCardStatus[12] = 2;
            intCardStatus[13] = 2;
            blnFound[6] = true;
            intCardsFlipped = 0;

          } else if (blnFound[6] == false && intCardDelay >= 30) {

            intCardStatus[12] = 0;
            intCardStatus[13] = 0;
            intCardsFlipped = 0;

          }
          
          if (intCardStatus[14] == 1 && intCardStatus[15] == 1 && intCardDelay >= 30) {

            intCardStatus[14] = 2;
            intCardStatus[15] = 2;
            blnFound[7] = true;
            intCardsFlipped = 0;

          } else if (blnFound[7] == false && intCardDelay >= 30)  {

            intCardStatus[14] = 0;
            intCardStatus[15] = 0;
            intCardsFlipped = 0;

          }
        
        // resets the delay 
        } else if (intCardsFlipped == 0) {

          intCardDelay = 0;

        }

      // turns all the cards back once the player has left the table before finishing it 
      } else if (blnTable == false) {

        Arrays.fill(intCardStatus,0);

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
      blnLeftLevel[0] = true;
      intPlayerX = 664;
      
    } else if (intLevel == 4 && intPlayerX < 16) {

      intLevel += 1;
      intPlayerX = 664;

    } else if (intLevel == 5 && intPlayerY <= 16 && blnNextLevel[2] == true) {

      intLevel += 1;
      intPlayerY = 664;
      blnLeftLevel[1] = true;

    } else if (intLevel == 6 && intPlayerY < 16) {

      intLevel += 1;
      intPlayerY = 664;

    } else if (intLevel == 7 && intPlayerX < 16 && blnNextLevel[3] == true) {

      intLevel += 2;
      intPlayerX = 664;
      blnLeftLevel[2] = true;

    } else if (intLevel == 10 && intPlayerY > 664 && blnNextLevel[4] == true) {

      intLevel += 1;
      intPlayerY = 16;
      blnLeftLevel[3] = true;

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

    } else if (intLevel == 7 && intPlayerY > 664) {

      intLevel -=1;
      intPlayerY = 16;

    } else if (intLevel == 9 && intPlayerX > 664) {

      // goes down by 2 so that the player ends up on the top floor and not the botto floor
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

    } 
    
    if (key == 'd' || key =='D') {
      
      blnRight = false;

    } 
    
    if (key == 'w' || key =='W') {
      
      blnUp = false;

    } 
    
    if (key == 's' || key =='S') {
      
      blnDown = false;

    } 
    
    if (key == 'e' || key == 'E') {

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

    if (blnGameStarting == false) {

      // detects if the player wants to leave the game
      if ((mouseX > 20 && mouseX < 75) && (mouseY > 10 && mouseY < 35)) {

        System.exit(0);

      }
      
      // detects if the player his hovering over the button on the starting screen 
      if (blnStartButtonPressed == false) {

        if ((mouseX > 240 && mouseX < 445) && (mouseY > 290 && mouseY < 365)) {

          blnStartButtonPressed = true;

        } 
      } else if (blnStartButtonPressed == true) {

        if ((mouseX > 250 && mouseX < 445) && (mouseY > 200 && mouseY < 275)) {

          blnEasy = true;
          blnGameStarting = true;

        } else if ((mouseX > 220 && mouseX < 470) && (mouseY > 300 && mouseY < 375)) {

          blnMedium = true;
          blnGameStarting = true;

        } else if ((mouseX > 250 && mouseX < 445) && (mouseY > 400 && mouseY < 475)) {

          blnHard = true;
          blnGameStarting = true;

        }
      }
    }

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

      // prevents the plaeyr from flipping more then 2 cards at a time 
      if (intCardDelay == 0) {

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
  }
  
  /**
   * sees which direction the player is currently moving in 
   * @return a boolean value as true that corresponds to the last direction that player as moving in 
   */
  public boolean blnMoving() {
    return blnUp || blnDown || blnLeft || blnRight;
  }
}
