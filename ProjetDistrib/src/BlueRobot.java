import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class BlueRobot extends Robot {
	
	private int yellowMarkerCounter = 0;
	private boolean lastWasYellow = false;
	
	protected BlueRobot(boolean direction, float colorFuzzy, float whiteFuzzy, float blackFuzzy, float yellowFuzzy, float pinkFuzzy) {
		super(direction, colorFuzzy, whiteFuzzy, blackFuzzy, yellowFuzzy, pinkFuzzy, 350);
		initializeColor(sensor, "blue");
		// TODO Auto-generated constructor stub
	}
	
	public void run() {
		
		float color[]= new float[3];
		sensor.getColor();
		int i=0;
		
		//boolean run = true;
		
		while(true) {
			color = sensor.getColor();
			if(isYellow(color)) {
				LCD.drawString("Jaune  ", 0, 1);
				changeDirection();
				if(!lastWasYellow) yellowMarkerCounter++;
				lastWasYellow = true;
				if(yellowMarkerCounter == 2) {
					changeDirection();
					yellowMarkerCounter = 0;
					givePriority();
				}
			}else if(isPink(color)) {
				LCD.drawString("Rose  ", 0, 1);
				control.forward();
				i=0;
				lastWasYellow = false;
			}else if(isBlack(color)) {
				LCD.drawString("Noir  ", 0, 1);
				if(direction) control.turnLeft();
				else control.turnRight();
				i=0;
				lastWasYellow = false;
			}else if(isWhite(color)){
				LCD.drawString("Blanc ", 0, 1);
				if(direction) control.turnRight();
				else control.turnLeft();
				i=0;
				lastWasYellow = false;
			}else if(isColorToFollow(color)) {
				LCD.drawString("Bleu  ", 0, 1);
				control.forward();
				i=0;
				lastWasYellow = false;
			}else {
				LCD.drawString("Rien  ", 0, 1);
				if(i>25) {
					control.stop();
					lastWasYellow = false;
				}
				i++;
			}
			LCD.drawString(String.format("%.3f", color[0]), 0, 3);
			LCD.drawString(String.format("%.3f", color[1]), 0, 4);
			LCD.drawString(String.format("%.3f", color[2]), 0, 5);
			Delay.msDelay(20);
		}
	}
	
}
