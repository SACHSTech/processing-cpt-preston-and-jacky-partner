import processing.core.PApplet;
import processing.core.PImage;

public class escape_room extends PApplet {
	
  PImage[] imgLevel;

  int intNumLevels = 1;
	
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
  // define other methods down here.
}
