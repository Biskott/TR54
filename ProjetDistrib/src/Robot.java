import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

import lejos.utility.Delay;

/**
 * Abstract class for robot
 * Common functions for robot (whatever his color)
 */
public abstract class Robot {

	protected RobotController control;
	protected Sensor sensor;
	protected Communication communication;

	// Robot direction
	protected boolean direction;

	// HashMap which define colors caracteristics
	protected HashMap<Color,float[]> colorList;

	/**
	 * Constructor
	 * @param direction : boolean, robot direction (from where to where)
	 * @param motorSpeed : robot speed in deg/s
	 */
	protected Robot(boolean direction, int motorSpeed) {

		this.direction = direction;

		control = new RobotController(motorSpeed);
		sensor = new Sensor();
		communication = new Communication();
	}

	/**
	 * Abstract function run
	 * Must be implemented in subclass
	 * Robot actions
	 */
	public abstract void run();

	/**
	 * Initialize color
	 * Set the markers's RGB values
	 * Is done by the user
	 * @param colorToFollow : Line color to follow
	 */
	protected void initializeColor(String colorToFollow) {

		colorList = new HashMap<Color, float[]>();

		LCD.drawString("Put on the " + colorToFollow,1, 3);
		LCD.drawString("track and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		colorList.put(Color.colorToFollow, sensor.getColor());
		LCD.drawString("Put on the black",1, 3);
		LCD.drawString("track and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		colorList.put(Color.black, sensor.getColor());
		LCD.drawString("Put on the white",1, 3);
		LCD.drawString("track and press",1, 4);
		LCD.drawString("any button",2, 5);
		Button.waitForAnyPress();
		colorList.put(Color.white, sensor.getColor());
		LCD.drawString("Put on the pink ",1, 3);
		LCD.drawString("marker and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		colorList.put(Color.pink, sensor.getColor());
		LCD.drawString("Put on the yellow",0, 3);
		LCD.drawString("marker and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		colorList.put(Color.yellow, sensor.getColor());
		LCD.clear();
		LCD.drawString("Loading ...",1, 3);
		Delay.msDelay(4000);
		LCD.clear();
	}

	/**
	 * Calculates distance between two colors
	 * @param tab1 : RGB value of the first color
	 * @param tab2 : RGB value of the first color
	 * @return : distance between two colors
	 */
	/*private float getDistance(float[] tab1, float[] tab2) {
		float part1 = (tab2[0] - tab1[0]) * (tab2[0] - tab1[0]);
		float part2 = (tab2[1] - tab1[1]) * (tab2[1] - tab1[1]);
		float part3 = (tab2[2] - tab1[2]) * (tab2[2] - tab1[2]);

		return (float) Math.sqrt(part1 + part2 + part3);
	}*/

	/**
	 * Get the nearest color from the list of the detected color
	 * @param value : RGB value of the detected color
	 * @return : name of the nearest color
	 */
	/*public Color getColorLibelle(float[] value) {

		Color colorDetected = Color.nothing;
		float minDistance = Float.MAX_VALUE;

		for(Color col : colorList.keySet()) {

			ArrayList<Float> rgbList = colorList.get(col);
			float[] rgb = {rgbList.get(0), rgbList.get(1), rgbList.get(2)};

			float actualDistance = getDistance(rgb, value);
			if(actualDistance < minDistance) {
				colorDetected = col;
				minDistance = actualDistance;
			}
		}
		return colorDetected;
	}*/
	
	/**
	 * Get the best color from the list of the detected color thanks to fuzzies
	 * @param value : RGB value of the detected color
	 * @return : name of the nearest color
	 */
	public Color getColorLibelle(float[] value) {
		if(isColorToFollow(value))
			return Color.colorToFollow;
		else if(isBlack(value))
			return Color.black;
		else if(isWhite(value))
			return Color.white;
		else if(isYellow(value))
			return Color.yellow;
		else if(isPink(value))
			return Color.pink;
		else
			return Color.nothing;
	}

	/**
	 * Change direction when the robot arrive to endpoint
	 */
	protected void changeDirection() {
		if(direction) control.rotateRight();
		else control.rotateLeft();
		while(getColorLibelle(sensor.getColor()) != Color.colorToFollow) {
			Delay.msDelay(10);
		}
		direction = !direction;
		control.stop();
	}

	/**
	 * Give priority to the other robot and stop until priority is given again
	 */
	protected void givePriority() {
		control.stop();
		communication.sendInt(15);
		Delay.msDelay(1000);
		communication.waitData();
	}
	
	/**
	 * Fonction isBlack
	 * Detect if the searched color is black with a particular fuzzy
	 * @param color : color to check
	 * @return : booleqn indicate if color is black
	 */
	protected boolean isBlack(float color[]) {
		float[] blackTrack = colorList.get(Color.black);
		float blackFuzzy = 0.2f;
		if (color[0]<blackTrack[0]*(1+blackFuzzy) && color[0]>blackTrack[0]*(1-blackFuzzy) && 
			color[1]<blackTrack[1]*(1+blackFuzzy) && color[1]>blackTrack[1]*(1-blackFuzzy) && 
			color[2]<blackTrack[2]*(1+blackFuzzy) && color[2]>blackTrack[2]*(1-blackFuzzy) ) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Fonction isWhite
	 * Detect if the searched color is white with a particular fuzzy
	 * @param color : color to check
	 * @return : booleqn indicate if color is white
	 */
	protected boolean isWhite(float color[]) {
		float[] whiteTrack = colorList.get(Color.white);
		float whiteFuzzy = 0.2f;
		if (color[0]<whiteTrack[0]*(1+whiteFuzzy) && color[0]>whiteTrack[0]*(1-whiteFuzzy) &&
			color[1]<whiteTrack[1]*(1+whiteFuzzy) && color[1]>whiteTrack[1]*(1-whiteFuzzy) &&
			color[2]<whiteTrack[2]*(1+whiteFuzzy) && color[2]>whiteTrack[2]*(1-whiteFuzzy) ) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Fonction isColorToFollow
	 * Detect if the searched color is the color to follow with a particular fuzzy
	 * @param color : color to check
	 * @return : booleqn indicate if color is the color to follow
	 */
	protected boolean isColorToFollow(float color[]) {
		float[] colorTrack = colorList.get(Color.colorToFollow);
		float colorFuzzy = 0.3f;
		if (color[0]<colorTrack[0]*(1+colorFuzzy) && color[0]>colorTrack[0]*(1-colorFuzzy) &&
			color[1]<colorTrack[1]*(1+colorFuzzy) && color[1]>colorTrack[1]*(1-colorFuzzy) &&
			color[2]<colorTrack[2]*(1+colorFuzzy) && color[2]>colorTrack[2]*(1-colorFuzzy) ) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Fonction isYellow
	 * Detect if the searched color is yellow with a particular fuzzy
	 * @param color : color to check
	 * @return : booleqn indicate if color is yellow
	 */
	protected boolean isYellow(float color[]) {
		float[] yellowMark = colorList.get(Color.yellow);
		float yellowFuzzy = 0.2f;
		if (color[0]<yellowMark[0]*(1+yellowFuzzy) && color[0]>yellowMark[0]*(1-yellowFuzzy) && 
			color[1]<yellowMark[1]*(1+yellowFuzzy) && color[1]>yellowMark[1]*(1-yellowFuzzy) && 
			color[2]<yellowMark[2]*(1+yellowFuzzy) && color[2]>yellowMark[2]*(1-yellowFuzzy) ) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Fonction isPink
	 * Detect if the searched color is pink with a particular fuzzy
	 * @param color : color to check
	 * @return : booleqn indicate if color is pink
	 */
	protected boolean isPink(float color[]) {
		float[] pinkMark = colorList.get(Color.pink);
		float pinkFuzzy = 0.3f;
		if (color[0]<pinkMark[0]*(1+pinkFuzzy) && color[0]>pinkMark[0]*(1-pinkFuzzy) && 
			color[1]<pinkMark[1]*(1+pinkFuzzy) && color[1]>pinkMark[1]*(1-pinkFuzzy) && 
			color[2]<pinkMark[2]*(1+pinkFuzzy) && color[2]>pinkMark[2]*(1-pinkFuzzy) ) {
			return true;
		}else {
			return false;
		}
	}
}
