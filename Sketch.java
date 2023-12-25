import processing.core.PApplet;

public class Sketch extends PApplet {
	
  // arrow key variable 
  boolean blnUpArrow = false;
  boolean blnDownArrow = false;
  boolean blnRightArrow = false;
  boolean blnLeftArrow = false;

  // movement key variable
  boolean blnUp = false;
  boolean blnDown = false;
  boolean blnLeft = false;
  boolean blnRight = false;

	
  /**
   * Called once at the beginning of execution, put your size all in this method
   */
  public void settings() {

	  // put your size call here
    size(800, 800);

  }

  /** 
   * Called once at the beginning of execution.  Add initial set up
   * values here i.e background, stroke, fill etc.
   */
  public void setup() {
    
  }

  /**
   * Called repeatedly, anything drawn to the screen goes here
   */
  public void draw() {
  
    System.out.println(blnUp);
	 
  }
  
  /**
   * takes certain key inputs and sets certain varaibles to true 
   */
  public void keyCode() {

    if (keyPressed) {

      if (keyCode == UP) {

        blnUpArrow = true;

      } if (keyCode == DOWN) {

        blnDownArrow = true;

      } if (keyCode == LEFT) {

        blnLeftArrow = true;

      } if (keyCode == RIGHT) {

        blnRightArrow = true;

      }
    }

  }

  /**
   * takes certain key inputs and sets certain varaibles to true 
   */
  public void keyPressed() {
    if (keyPressed) {

      if (key == 'w' || key == 'W') {

        blnUp = true;

      } if (key == 'd' || key == 'D') {

        blnRight = true;

      } if (key == 's' || key == 'S') {

        blnDown = true;

      } if (key == 'a' || key == 'A') {

        blnLeft = true;

      }
    }
  }

  /**
   * detects that a key is released and sets certain variables to false 
   */
  public void keyReleased() {

    if (keyCode == UP) {

        blnUpArrow = false;

    } if (keyCode == DOWN) {

      blnDownArrow = false;

    } if (keyCode == LEFT) {

      blnLeftArrow = false;

    } if (keyCode == RIGHT) {

      blnRightArrow = false;

    } if (key == 'w' || key == 'W') {

      blnUp = false;

    } if (key == 'd' || key == 'D') {

      blnRight = false;

    } if (key == 's' || key == 'S') {

      blnDown = false;

    } if (key == 'a' || key == 'A') {

      blnLeft = false;
      
    }

  }
}
