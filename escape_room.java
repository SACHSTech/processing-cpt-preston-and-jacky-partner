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
  boolean blnGameStarting = false;

  // number of levels
  int intNumLevels = 10;

  // current level
  int intLevel = 0;

  // health
  int intHealth = 100;

  // number of frames for each player animation 
  int intNumFrames = 4;
  int intMoveFrames = 0;

  // player position 
  int intPlayerX = 300;
  int intPlayerY = 300;
	
  // movement booleans 
  boolean blnUp, blnDown, blnLeft, blnRight;

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

    if (blnGameStarting == true) {
      
      drawCollisionMaps();
      playerMovementAndCollisions();
      playerInteractions();
      drawMaps();
      playerUpdate();

    } else {
      
    }

  }

  /**
   * draws the needed collision map to match with the player map
   */
  public void drawCollisionMaps() {

    // draws the collision maps 
    image(imgLevelCollision[intLevel],0,0);

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
  
    
    if (intPlayerX <= 16) {

      intLevel += 1;
      intPlayerX = 664;
      
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

    if (key == 'e' || key == 'E') {

      if (intLevel == 2) {

        if (intPlayerY > height / 2) {

          // desk pop up 
          // image();

        } else {

          // safe pop up 
          // image();

        }
      } else if (intLevel == 5) {

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

    }
  }

  public boolean blnMoving() {
    return blnUp || blnDown || blnLeft || blnRight;
  }
}
