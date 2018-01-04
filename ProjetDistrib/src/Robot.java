import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

import lejos.utility.Delay;

/**
 * Classe abstraite Robot
 * Elle contient les fonctions de base définissant un robot (peu importe son type)
 */
public abstract class Robot {

	// Attributs utilisés pour la gestion des capteurs, les déplacements et la communication
	protected RobotController control;
	protected Sensor sensor;
	protected Communication communication;

	// Attributs utilisés pour la gestion de la direction du robot
	protected boolean direction;
	
	// HashMap correspondant aux valeurs RGB des diff�rentes couleurs
	protected HashMap<Color, ArrayList<Float>> colorList;

	/**
	 * Constructeur de la classe Robot
	 * @param direction : booléen indiquant le sens dans lequel doit tourner le robot
	 * @param motorSpeed : vitesse de rotation des moteurs
	 */
	protected Robot(boolean direction, int motorSpeed) {
		
		this.direction = direction;
		
		control = new RobotController(motorSpeed);
		sensor = new Sensor();
		communication = new Communication();
	}

	/**
	 * Fonction abstraite run
	 * Elle doit être implémenter dans les classes filles et sert à gérer les actions du robot
	 */
	public abstract void run();

	/**
	 * Fonction initializeColor
	 * Elle permet de détecter les valeurs RGB de chaque couleur au démarrage du robot
	 * Cela est fait manuellement par l'utilisateur
	 * @param colorToFollow : Nom de la couleur à suivre par le robot
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
	 * Fonction getDistance
	 * Elle permet de calculer la distance entre deux valeurs RGB
	 * @param tab1 : valeurs RGB de la premi�re composante
	 * @param tab2 : valeurs RGB de la deuxi�me composante
	 * @return : renvoie la distance entre les deux valeurs
	 */
	private float getDistance(float[] tab1, float[] tab2) {
		float part1 = (tab2[0] - tab1[0]) * (tab2[0] - tab1[0]);
		float part2 = (tab2[1] - tab1[1]) * (tab2[1] - tab1[1]);
		float part3 = (tab2[2] - tab1[2]) * (tab2[2] - tab1[2]);
		
		return (float) Math.sqrt(part1 + part2 + part3);
	}
	
	/**
	 * Fonction getColorLibelle
	 * Elle permet de d�tecter la couleur de la liste la plus proche de celle recherch�e
	 * @param value : valeur RGB de la couleur � trouver
	 * @return : Le nom de la couleur la plus proche de ses valeurs RGB
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
	 * Fonction changeDirection
	 * Permet de changer la direction du robot quand il arrive en fin de trajet
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
	 * Fonction givePriority
	 *
	 */
	protected void givePriority() {
		control.stop();
		communication.sendInt(15);
		Delay.msDelay(1000);
		communication.waitData();
	}
}
