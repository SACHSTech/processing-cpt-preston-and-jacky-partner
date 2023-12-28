import processing.core.PApplet;
import processing.core.PImage;

public class Sketch1 extends PApplet {

  PImage[] imgLevel;
  PImage[] imgLevelCollision;

  int intNumLevels = 1;

  float fltPlayerX = 500, fltPlayerY = 500; 
  float fltPlayerVelX, fltPlayerVelY;

  boolean blnW, blnA, blnD = false;

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
    background(0,100,255);
    drawLevelCollision();
    playerCollision();
    playerMovement();
    drawLevel();
    updatePlayer();
    //fltCollisionColor == -1.6777216E7 this is black
  }

  public void drawLevelCollision() {
    image(imgLevelCollision[0], 0, 0);
  }
  public void drawLevel() {
    image(imgLevel[0], 0, 0);
  }
  
  public void playerCollision() {

    if (get((int)(fltPlayerX), (int)(fltPlayerY + 32)) == -1.6777216E7 || get((int)(fltPlayerX + 16), (int)(fltPlayerY + 32)) == -1.6777216E7) {
      fltPlayerVelY = 0;
    } else {
      fltPlayerVelY --;
    }
    
  }

  public void playerMovement() {

    fltPlayerY -= fltPlayerVelY;

    if (blnW == true) {

      fltPlayerY -= 12;

    } 
    
    if (blnA == true) {

      fltPlayerX -= 10;

    }
    
    if (blnD == true) {

      fltPlayerX += 10;

    }

  }

  public void keyPressed() {

    if (key == 'w' || key == 'W') {

      blnW = true;

    } 
    
    if (key == 'a' || key == 'A') {

      blnA = true;

    }
    
    if (key == 'd' || key == 'D') {

      blnD = true;

    }

  }

  public void keyReleased() {

    if (key == 'w' || key == 'W') {

      blnW = false;

    } 
    
    if (key == 'a' || key == 'A') {

      blnA = false;

    }
    
    if (key == 'd' || key == 'D') {

      blnD = false;

    }

  }

  public void updatePlayer() {
    noStroke();
    fill(255);
    rect(fltPlayerX, fltPlayerY, 16 ,32);
  }
}
