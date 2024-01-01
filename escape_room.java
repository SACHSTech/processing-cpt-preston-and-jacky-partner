import processing.core.PApplet;
import processing.core.PImage;

public class escape_room extends PApplet {
	
  PImage[] imgLevel;
  PImage[] imgPlayerMovement;

  int intNumLevels = 1;
	
  boolean blnUp, blnDown, blnLeft, blnRight = false;

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
    image(imgLevel[0], 0, 0);
  }
  
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
