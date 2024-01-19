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
	
	//Sketch mySketch = new Sketch();  //comment this out to run the other sketch files
	//platformer mySketch = new platformer();  // uncomment this to run this sketch file
	escape_room mySketch2 = new escape_room();  // uncomment this to run this sketch file
	  
	//PApplet.runSketch(processingArgs, mySketch);
		PApplet.runSketch(processingArgs, mySketch2);

 }
}
