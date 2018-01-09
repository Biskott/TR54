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

	// HashMap correspondant aux valeurs RGB des diff�rentes couleurs
	protected HashMap<Color, ArrayList<Float>> colorList;

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

		colorList = new HashMap<Color, ArrayList<Float>>();

		LCD.drawString("Put on the " + colorToFollow,1, 3);
		LCD.drawString("track and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		colorList.put(Color.colorToFollow, new ArrayList<Float>(Arrays.asList(s.getColor())));
		LCD.drawString("Put on the black",1, 3);
		LCD.drawString("track and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		colorList.put(Color.black, new ArrayList<Float>(Arrays.asList(s.getColor())));
		LCD.drawString("Put on the white",1, 3);
		LCD.drawString("track and press",1, 4);
		LCD.drawString("any button",2, 5);
		Button.waitForAnyPress();
		colorList.put(Color.white, new ArrayList<Float>(Arrays.asList(s.getColor())));
		LCD.drawString("Put on the pink ",1, 3);
		LCD.drawString("marker and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		colorList.put(Color.pink, new ArrayList<Float>(Arrays.asList(s.getColor())));
		LCD.drawString("Put on the yellow",0, 3);
		LCD.drawString("marker and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		colorList.put(Color.yellow, new ArrayList<Float>(Arrays.asList(s.getColor())));
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
	private float getDistance(float[] tab1, float[] tab2) {
		float part1 = (tab2[0] - tab1[0]) * (tab2[0] - tab1[0]);
		float part2 = (tab2[1] - tab1[1]) * (tab2[1] - tab1[1]);
		float part3 = (tab2[2] - tab1[2]) * (tab2[2] - tab1[2]);

		return (float) Math.sqrt(part1 + part2 + part3);
	}

	/**
	 * Get the nearest color from the list of the detected color
	 * @param value : RGB value of the detected color
	 * @return : name of the nearest color
	 */
	public Color getColorLibelle(float[] value) {

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
}
