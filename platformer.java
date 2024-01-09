import processing.core.PApplet;
import processing.core.PImage;

public class platformer extends PApplet {

  PImage[] imgLevel;
  PImage[] imgLevelCollision;

  int intNumLevels = 1;

  float fltPlayerX = 500, fltPlayerY = 500; 
  float fltPlayerGravity;

  boolean blnW, blnA, blnD = false;

  public void settings() {
    
    size(1200, 672);

  }

  public void setup() {

    imgLevel = new PImage[intNumLevels];
    imgLevelCollision = new PImage[intNumLevels];

    for (int i = 0; i < intNumLevels; i++) {
      imgLevel[i] = loadImage("platformer/levels/level" + i + ".png"); 
    }

    for (int i = 0; i < intNumLevels; i++) {
      imgLevelCollision[i] = loadImage("platformer/levels/collision/levelCollision" + i + ".png"); 
    }
  }

  
  public void draw() {
    background(0,100,255);
    drawLevelCollision();
    playerMovementAndCollision();
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

  public void playerMovementAndCollision() {

    if (blnW == true) {

      fltPlayerY -= 16;
      
    } 

    if (get((int)(fltPlayerX), (int)(fltPlayerY + 32)) == -1.6777216E7 || get((int)(fltPlayerX + 16), (int)(fltPlayerY + 32)) == -1.6777216E7) {
      
      fltPlayerGravity = 0;
      fltPlayerY -= 1;

    } else {

      fltPlayerGravity -= 1;
      
      if (fltPlayerGravity <= -25) {

        fltPlayerGravity = -25;

      } 
    }
    
    if (blnA == true && fltPlayerX - 16 > 0) {

      fltPlayerX -= 8;

    }
    
    if (blnD == true && fltPlayerX + 10 < width) {

      fltPlayerX += 8;

    }

    fltPlayerY -= fltPlayerGravity;

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
