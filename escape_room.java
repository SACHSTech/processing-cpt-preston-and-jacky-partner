import processing.core.PApplet;
import processing.core.PImage;

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
  
  // number of levels
  int intNumLevels = 1;

  // number of frames for each player animation 
  int intNumFrames = 4;
  int intNumAttackFrames = 4;

  // player position 
  int intPlayerX = 0;
  int intPlayerY = 0;
	
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

      imgLevel[i] = loadImage("escape_room/levels/collisions/level" + i + ".png"); 

    }

    if (blnLeft == true) {

      for (int i = 0; i < intNumFrames; i++) {
        
        imgPlayerLeft[i] = loadImage("" + i + ".png");

      }
    } 
    
    if (blnRight == true) {

      for (int i = 0; i < intNumFrames; i++) {
        
        imgPlayerRight[i] = loadImage("" + i + ".png");

      }
    } 
    
    if (blnUp == true) {
        
      for (int i = 0; i < intNumFrames; i++) {
        
        imgPlayerUp[i] = loadImage("" + i + ".png");

      }
    } 
    
    if (blnDown == true) {

      for (int i = 0; i < intNumFrames; i++) {
        
        imgPlayerDown[i] = loadImage("" + i + ".png");

      }
    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {

    // prints out the correct room depending on the level the player is on 
    image(imgLevel[0], 0, 0);

     //draws the animation for moving left
    if (blnLeft == true) {

      image(imgPlayerLeft[0],intPlayerX,intPlayerY);
      
    } 
    
    //draws the animation for moving right
    if (blnRight == true) {

      image(imgPlayerRight[0],intPlayerX,intPlayerY);
      
    } 
    
    //draws the animation for moving up
    if (blnUp == true) {
        
      image(imgPlayerUp[0],intPlayerX,intPlayerY);
      
    } 
    
    //draws the animation for moving down
    if (blnDown == true) {

      image(imgPlayerDown[0],intPlayerX,intPlayerY);
      
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
