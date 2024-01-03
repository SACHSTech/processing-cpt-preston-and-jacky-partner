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
      
      //imgPlayerLeft[i] = loadImage("escape_room/player/playerLeft" + i + ".png");

    }
  
  
    for (int i = 0; i < intNumFrames; i++) {
      
      //imgPlayerRight[i] = loadImage("escape_room/player/playerRight" + i + ".png");

    }
      
    for (int i = 0; i < intNumFrames; i++) {
      
      //imgPlayerUp[i] = loadImage("escape_room/player/playerUp" + i + ".png");

    }
  
    for (int i = 0; i < intNumFrames; i++) {
      
      imgPlayerDown[i] = loadImage("escape_room/player/playerDown" + i + ".png");

    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {

    DrawCollisionMaps();
    PlayerInteractions();
    DrawMaps();
    HotbarInteractions();
    PlayerUpdate();

  }

  /**
   * draws the needed maps for the level that the player is on 
   */
  public void DrawMaps() {

    // prints out the correct room depending on the level the player is on 
    image(imgLevel[0],0,0);

  }

  /**
   * draws the needed collision map to match with the player map
   */
  public void DrawCollisionMaps() {

    // draws the collision maps 
    image(imgLevelCollision[0],0,0);


  }

  /**
   * Used to detect player interactions and respond accordingly 
   */
  public void PlayerInteractions() {

    // mob detection 
    if (get(intPlayerX, intPlayerY) == -6.5536e4) {

      // damage limiter when mobs damage player 
      if (frameCount % 60 == 0) {

        intHealth = intHealth - 10;

      }
    }

    // checks below the character to make sure they are in bounds at all times
    if ((get(intPlayerX, intPlayerY + 32) == -1.6777216E7 && blnDown == true) || (get(intPlayerX + 16, intPlayerY + 32) == -1.6777216E7 && blnDown == true)) {

      intPlayerY = height;

    // checks above the character to make sure they are in bounds at all times
    } if ((get(intPlayerX, intPlayerY - 32) == -1.6777216E7 && blnUp == true) || (get(intPlayerX + 16, intPlayerY - 32) == -1.6777216E7 && blnUp == true)) {

      intPlayerY = 16;
    
    // checks to the right of the character to make sure they are in bounds at all times
    } if ((get(intPlayerX + 32, intPlayerY) == -1.6777216E7 && blnRight == true) || (get(intPlayerX + 32, intPlayerY + 16) == -1.6777216E7 && blnRight == true)) {

      intPlayerX = width;

    // checks to the left of the character to make sure they are in bounds at all times 
    } if ((get(intPlayerX - 32, intPlayerY) == -1.6777216E7 && blnLeft == true) || (get(intPlayerX - 32, intPlayerY + 16) == -1.6777216E7 && blnLeft == true)) {
      
      intPlayerX = 16;

    }
  }

  /**
   * updates how the player looks and at the correct location 
   */
  public void PlayerUpdate() {
    
    //draws the animation for moving left
    if (blnLeft == true) {

      image(imgPlayerLeft[0], intPlayerX, intPlayerY);
      intPlayerX -= 5;
      
    } 
    
    //draws the animation for moving right
    if (blnRight == true) {

      image(imgPlayerRight[0], intPlayerX, intPlayerY);
      intPlayerX += 5;
      
    } 
    
    //draws the animation for moving up
    if (blnUp == true) {
        
      image(imgPlayerUp[0], intPlayerX, intPlayerY);
      intPlayerY += 5;
      
    } 
    
    //draws the animation for moving down
    if (blnDown == true) {

      image(imgPlayerDown[0], intPlayerX, intPlayerY);
      intPlayerY -= 5;
      
    }
  }

  /**
  * hotbar interactions 
  */
  public void HotbarInteractions() {

    for (int i = 0; i < intHotbarSpace; i++) {

      // detects if the item that you are standing on top of is yellow
      if (get(intPlayerX,intPlayerY) == -256) {

        // finds an empty slow in your inventory and replaces it with the item 
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

  /**
   * detects which keys are pressed and then sets certain boolean values to true
   */
  public void KeyPressed() {

    if (keyPressed) {

      if (key == 'a' || key =='A') {

        blnLeft = true;

      } if (key == 'd' || key =='D') {
        
        blnRight = true;

      } if (key == 'w' || key =='W') {
        
        blnUp = true;

      } if (key == 's' || key =='S') {
        
        blnDown = true;

      }
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
