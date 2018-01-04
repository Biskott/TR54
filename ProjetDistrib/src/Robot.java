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
	protected float colorTrack[], blackTrack[], whiteTrack[], yellowMark[], pinkMark[] = new float[3];
	protected boolean direction;

	// Attributs correspondant aux marges pour la reconnaissance des couleurs
	protected float colorFuzzy = 0.3f;
	protected float whiteFuzzy = 0.3f;
	protected float blackFuzzy = 0.3f;
	protected float yellowFuzzy = 0.3f;
	protected float pinkFuzzy = 0.3f;

	/**
	 * Constructeur de la classe Robot
	 * @param direction : booléen indiquant le sens dans lequel doit tourner le robot
	 * @param colorFuzzy : marge de détection de la couleur à suivre
	 * @param whiteFuzzy : marge de détection dela couleur blanche
	 * @param blackFuzzy : marge de détection de la couleur noire
	 * @param yellowFuzzy : marge de détection de la couleur jaune
	 * @param pinkFuzzy : marge de détection de la couleur rose
	 * @param motorSpeed : vitesse de rotation des moteurs
	 */
	protected Robot(boolean direction, float colorFuzzy, float whiteFuzzy, float blackFuzzy, 
			float yellowFuzzy, float pinkFuzzy, int motorSpeed) {
		this.colorFuzzy = colorFuzzy;
		this.whiteFuzzy = whiteFuzzy;
		this.blackFuzzy = blackFuzzy;
		this.yellowFuzzy = yellowFuzzy;
		this.pinkFuzzy = pinkFuzzy;
		
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
		colorTrack=s.getColor();
		LCD.drawString("Put on the " + colorToFollow,1, 3);
		LCD.drawString("track and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		colorTrack=s.getColor();
		LCD.drawString("Put on the black",1, 3);
		LCD.drawString("track and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		blackTrack=s.getColor();
		LCD.drawString("Put on the white",1, 3);
		LCD.drawString("track and press",1, 4);
		LCD.drawString("any button",2, 5);
		Button.waitForAnyPress();
		whiteTrack=s.getColor();
		LCD.drawString("Put on the pink ",1, 3);
		LCD.drawString("marker and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		pinkMark=s.getColor();
		LCD.drawString("Put on the yellow",0, 3);
		LCD.drawString("marker and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		yellowMark=s.getColor();
		LCD.clear();
		LCD.drawString("Loading ...",1, 3);
		Delay.msDelay(4000);
		LCD.clear();
	}

	/**
	 * Fonction isBlack
	 * Elle permet de détecter si une couleur est identifié comme du noir
	 * @param color : la couleur à tester
	 * @return : booléen correspondant à la réponse au test
	 */
	protected boolean isBlack(float color[]) {
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
	 * Elle permet de détecter si une couleur est identifié comme du blanche
	 * @param color : la couleur à tester
	 * @return : booléen correspondant à la réponse au test
	 */
	protected boolean isWhite(float color[]) {
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
	 * Elle permet de détecter si une couleur est identifié comme la couleur à suivre par le robot
	 * @param color : la couleur à tester
	 * @return : booléen correspondant à la réponse au test
	 */
	protected boolean isColorToFollow(float color[]) {
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
	 * Elle permet de détecter si une couleur est identifié comme du jaune
	 * @param color : la couleur à tester
	 * @return : booléen correspondant à la réponse au test
	 */
	protected boolean isYellow(float color[]) {
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
	 * Elle permet de détecter si une couleur est identifié comme du rose
	 * @param color : la couleur à tester
	 * @return : booléen correspondant à la réponse au test
	 */
	protected boolean isPink(float color[]) {
		if (color[0]<pinkMark[0]*(1+pinkFuzzy) && color[0]>pinkMark[0]*(1-pinkFuzzy) && 
			color[1]<pinkMark[1]*(1+pinkFuzzy) && color[1]>pinkMark[1]*(1-pinkFuzzy) && 
			color[2]<pinkMark[2]*(1+pinkFuzzy) && color[2]>pinkMark[2]*(1-pinkFuzzy) ) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Fonction changeDirection
	 * Permet de changer la direction du robot quand il arrive en fin de trajet
	 */
	protected void changeDirection() {
		if(direction) control.rotateRight();
		else control.rotateLeft();
		while(!isColorToFollow(sensor.getColor())) {
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
