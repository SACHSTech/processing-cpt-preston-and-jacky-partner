import processing.core.PApplet;
import processing.core.PImage;

public class Sketch1 extends PApplet {

  PImage[] imgLevel;
  int intNumLevels = 1;
  
  public void settings() {
    
    size(1200, 720);

  }

  public void setup() {

    imgLevel = new PImage[intNumLevels];

    for (int i = 0; i < intNumLevels; i++) {
      imgLevel[i] = loadImage("levels/level" + i + ".png"); 
    }
    
  }

  
  public void draw() {
	  image(imgLevel[0], 0, 0);
  }
  
  // define other methods down here.
}
