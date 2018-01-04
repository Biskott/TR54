import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

/**
 * Classe BlueRobot
 * Elle hérite de la classe Robot et permet la gestion des robots suivants le trajet bleu
 */
public class BlueRobot extends Robot {

	// Attribut permettant la gestion des marqueurs jaunes
	private int yellowMarkerCounter = 0;
	private boolean lastWasYellow = false;

	/**
	 * Constructeur de la classe BlueRobot
	 * @param direction : booléen indiquant le sens dans lequel doit tourner le robot
	 */
	protected BlueRobot(boolean direction) {
		super(direction, 350);
		initializeColor("blue");
	}

	/**
	 * Fonction run
	 * Elle permet la gestion du trajet du robot et la communication avec les autres robots
	 */
	public void run() {
		
		float color[]= new float[3];
		//sensor.getColor();
		int i=0;
		
		while(true) {
			
			switch(getColorLibelle(sensor.getColor())) {
			
			case yellow:
				LCD.drawString("Jaune  ", 0, 1);
				changeDirection();
				if(!lastWasYellow) yellowMarkerCounter++;
				lastWasYellow = true;
				if(yellowMarkerCounter == 2) {
					changeDirection();
					yellowMarkerCounter = 0;
					givePriority();
				}
				break;
			case pink:
				LCD.drawString("Rose  ", 0, 1);
				control.forward();
				i=0;
				lastWasYellow = false;
				break;
			case black:
				LCD.drawString("Noir  ", 0, 1);
				if(direction) control.turnLeft();
				else control.turnRight();
				i=0;
				lastWasYellow = false;
				break;
			case white:
				LCD.drawString("Blanc ", 0, 1);
				if(direction) control.turnRight();
				else control.turnLeft();
				i=0;
				lastWasYellow = false;
				break;
			case colorToFollow:
				LCD.drawString("Bleu  ", 0, 1);
				control.forward();
				i=0;
				lastWasYellow = false;
				break;
			default:
				LCD.drawString("Rien  ", 0, 1);
				if(i>25) {
					control.stop();
					lastWasYellow = false;
				}
				i++;
				break;	
			}
			LCD.drawString(String.format("%.3f", color[0]), 0, 3);
			LCD.drawString(String.format("%.3f", color[1]), 0, 4);
			LCD.drawString(String.format("%.3f", color[2]), 0, 5);
			Delay.msDelay(20);
		}
	}
	
}
