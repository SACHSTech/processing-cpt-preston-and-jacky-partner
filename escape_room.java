import processing.core.PApplet;
import processing.core.PImage;
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

  // hotbar array
  String[] strHotbar = {"pot1","","pot3","pot4"};

  // max hotbar space
  int intHotbarSpace = 4;

  // number of levels
  int intNumLevels = 4;

  // health
  int intHealth = 100;

  // number of frames for each player animation 
  int intNumFrames = 4;
  int intNumAttackFrames = 4;

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

    frameRate(60);

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

    }
  
  
    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerRight[i] = loadImage("escape_room/player/playerRight" + i + ".png");

    }
      
    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerUp[i] = loadImage("escape_room/player/playerUp" + i + ".png");

    }
  
    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerDown[i] = loadImage("escape_room/player/playerDown" + i + ".png");

    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {

    drawCollisionMaps();
    playerMovementAndCollisions();
    drawMaps();
    hotbarInteractions();
    playerUpdate();
  }

  /**
   * draws the needed collision map to match with the player map
   */
  public void drawCollisionMaps() {

    // draws the collision maps 
    image(imgLevelCollision[0],0,0);


  }

  /**
   * movement for the player and checks for collisions
   */
  public void playerMovementAndCollisions() {
    
    if (blnLeft == true && (get(intPlayerX - 8, intPlayerY + 36) != -1.6777216E7)) {

      intPlayerX -= 8;
      
    } 
    
    if (blnRight == true && (get(intPlayerX + 36, intPlayerY + 36) != -1.6777216E7)) {

      intPlayerX += 8;
      
    } 
    
    if (blnUp == true && (get(intPlayerX, intPlayerY + 28) != -1.6777216E7 && get(intPlayerX + 28, intPlayerY + 28) != -1.6777216E7)) {
        
      intPlayerY -= 8;
      
    } 
    
    
    if (blnDown == true && (get(intPlayerX, intPlayerY + 44) != -1.6777216E7 && get(intPlayerX + 28, intPlayerY + 44) != -1.6777216E7)) {

      intPlayerY += 8;
      
    }
  }

  /**
   * draws the nescessary maps for the level that the player is on 
   */
  public void drawMaps() {

    // draws out the correct room depending on the level the player is on 
    image(imgLevel[0],0,0);

  }

  /**
  * hotbar interactions 
  */
  public void hotbarInteractions() {

    for (int i = 0; i < intHotbarSpace; i++) {

      // detects if the item that you are standing on top of is yellow
      if (get(intPlayerX,intPlayerY) == -256) {

        // finds an empty slot in your inventory and replaces it with the item 
        if (strHotbar[i] == "") {

          strHotbar[i] = "RedPot";

        }
      }
    }

    // allows you to drop things from you inventory 
    if (key == '1') {

      if (key == 'q' || key == 'Q') {

        strHotbar[0] = "";

      }
    }

    // allows you to drop things from you inventory 
    if (key == '2') {

      if (key == 'q' || key == 'Q') {

        strHotbar[1] = "";

      }
    }

    // allows you to drop things from you inventory 
    if (key == '3') {

      if (key == 'q' || key == 'Q') {

        strHotbar[2] = "";

      }
    }

    // allows you to drop things from you inventory 
    if (key == '4') {

      if (key == 'q' || key == 'Q') {

        strHotbar[3] = "";

      }
    }

  }

  public void playerUpdate() {
    image(imgPlayerLeft[0], intPlayerX, intPlayerY);
  }

  /**
   * detects which keys are pressed and then sets certain boolean values to true
   */
  public void keyPressed() {
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
}
