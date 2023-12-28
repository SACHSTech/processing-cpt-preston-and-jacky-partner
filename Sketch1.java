import processing.core.PApplet;
import processing.core.PImage;

public class Sketch1 extends PApplet {

  PImage[] imgLevel;
  PImage[] imgLevelCollision;

  int intNumLevels = 1;

  float fltCollisionColor;
  
  public void settings() {
    
    size(1200, 672);

  }

  public void setup() {

    imgLevel = new PImage[intNumLevels];
    imgLevelCollision = new PImage[intNumLevels];

    for (int i = 0; i < intNumLevels; i++) {
      imgLevel[i] = loadImage("levels/level" + i + ".png"); 
    }

    for (int i = 0; i < intNumLevels; i++) {
      imgLevelCollision[i] = loadImage("levels/collision/levelCollision" + i + ".png"); 
    }
  }

  
  public void draw() {
    image(imgLevelCollision[0], 0, 0);
    fltCollisionColor = get(mouseX,mouseY);
    System.out.print(fltCollisionColor);
	  image(imgLevel[0], 0, 0);

  }
  
  // define other methods down here.
}
