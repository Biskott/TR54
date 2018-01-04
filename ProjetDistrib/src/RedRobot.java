import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

/**
 * Classe BlueRobot
 * Elle hérite de la classe Robot et permet la gestion des robots suivants le trajet rouge
 */
public class RedRobot extends Robot {

	// Attribut permettant la gestion des marqueurs roses
	private int markerCounter = 0;
	private boolean lastWasPink = false;

	/**
	 * Constructeur de la classe RedRobot
	 * @param direction : booléen indiquant le sens dans lequel doit tourner le robot
	 * @param colorFuzzy : marge de détection de la couleur à suivre (rouge)
	 * @param whiteFuzzy : marge de détection dela couleur blanche
	 * @param blackFuzzy : marge de détection de la couleur noire
	 * @param yellowFuzzy : marge de détection de la couleur jaune
	 * @param pinkFuzzy : marge de détection de la couleur rose
	 */
	protected RedRobot(boolean direction, float colorFuzzy, float whiteFuzzy, float blackFuzzy, float yellowFuzzy, float pinkFuzzy) {
		super(direction, colorFuzzy, whiteFuzzy, blackFuzzy, yellowFuzzy, pinkFuzzy, 350);
		initializeColor("red");
	}

	/**
	 * Fonction run
	 * Elle permet la gestion du trajet du robot et la communication avec les autres robots
	 */
	public void run() {
		
		float color[]= new float[3];
		int i=0;
		
		while(true) {
			color = sensor.getColor();
			if(isYellow(color)) {
				LCD.drawString("Jaune  ", 0, 1);
				changeDirection();
				givePriority();
				markerCounter = 0;
				lastWasPink = false;
			}else if(isPink(color)) {
				LCD.drawString("Rose  ", 0, 1);
				if(!lastWasPink) markerCounter++;
				lastWasPink = true;
				if(markerCounter == 2) {
					changeDirection();
					markerCounter = 0;
				}
			}else if(isBlack(color)) {
				LCD.drawString("Noir  ", 0, 1);
				if(direction) control.turnLeft();
				else control.turnRight();
				i=0;
				lastWasPink = false;
			}else if(isWhite(color)){
				LCD.drawString("Blanc ", 0, 1);
				if(direction) control.turnRight();
				else control.turnLeft();
				i=0;
				lastWasPink = false;
			}else if(isColorToFollow(color)) {
				LCD.drawString("Rouge  ", 0, 1);
				control.forward();
				i=0;
				lastWasPink = false;
			}else {
				LCD.drawString("Rien  ", 0, 1);
				if(i>25) {
					control.stop();
					lastWasPink = false;
				}
				i++;
			}
			LCD.drawString(String.format("%.3f", color[0]), 0, 3);
			LCD.drawString(String.format("%.3f", color[1]), 0, 4);
			LCD.drawString(String.format("%.3f", color[2]), 0, 5);
			LCD.drawString("Counter : " + String.format("%d", markerCounter), 0, 6);
			Delay.msDelay(20);
		}
	}
}
