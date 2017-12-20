import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class RedRobot extends Robot {
	
	private int markerCounter = 0;
	private boolean lastWasPink = false;

	protected RedRobot(boolean direction, float colorFuzzy, float whiteFuzzy, float blackFuzzy, float yellowFuzzy, float pinkFuzzy) {
		super(direction, colorFuzzy, whiteFuzzy, blackFuzzy, yellowFuzzy, pinkFuzzy, 350);
		// TODO Auto-generated constructor stub
	}

public void run() {
		
		float color[]= new float[3];
		sensor.getColor();
		int i=0;
		
		initializeColor(sensor, "red");
		
		while(true) {
			color = sensor.getColor();
			if(isYellow(color)) {
				LCD.drawString("Jaune  ", 0, 1);
				changeDirection();
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
