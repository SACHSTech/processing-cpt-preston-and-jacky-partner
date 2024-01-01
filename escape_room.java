import processing.core.PApplet;
import processing.core.PImage;

public class escape_room extends PApplet {
	
  PImage[] imgLevel;
  PImage[] imgPlyaerLeft;
  PImage[] imgPlyaerRight;
  PImage[] imgPlyaerUp;
  PImage[] imgPlayerDown;

  int intNumLevels = 1;
  int intNumFrames = 5;
	
  boolean blnUp, blnDown, blnLeft, blnRight, blnMouseClicked = false;

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
    imgLevel = new PImage[intNumLevels];

    for (int i = 0; i < intNumLevels; i++) {
      imgLevel[i] = loadImage("escape_room/levels/level" + i + ".png"); 
    }
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {

    // checks if the mouse button has been pressed
    if (mousePressed) {

      blnMouseClicked = true;

    } else {

      blnMouseClicked = false;
      
    }

    image(imgLevel[0], 0, 0);

    //draws the animation for moving left
    if (blnLeft == true) {

      for (int i = 0; i < intNumFrames; i++) {
        
        //imgPlyaerLeft = loadImage(" " + i + ".png");

      }
    } 
    
    //draws the animation for moving right
    if (blnRight == true) {

      for (int i = 0; i < intNumFrames; i++) {
        
        //imgPlyaerRight = loadImage(" " + i + ".png");

      }
    } 
    
    //draws the animation for moving up
    if (blnUp == true) {
        
      for (int i = 0; i < intNumFrames; i++) {
        
        //imgPlyaerUp = loadImage(" " + i + ".png");

      }
    } 
    
    //draws the animation for moving down
    if (blnDown == true) {

      for (int i = 0; i < intNumFrames; i++) {
        
        //imgPlyaerDown = loadImage(" " + i + ".png");

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
