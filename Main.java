import processing.core.PApplet;

/**
 * Main class to execute sketch
 * @author Preston and Jacky
 *
 */
class Main {

	escape_room Start = new escape_room();

  public static void main(String[] args) {

    String[] processingArgs = {"MySketch"};
	escape_room mySketch = new escape_room();  
	PApplet.runSketch(processingArgs, mySketch);

 }
}
