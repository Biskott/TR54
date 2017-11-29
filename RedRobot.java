import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class RedRobot {

	private static float redTrack[], blackTrack[], whiteTrack[] = new float[3];
	
	private static void initializeColor(Sensor s) {
		LCD.drawString("Put on the red",1, 3);
		LCD.drawString("track and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		redTrack=s.getColor();
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
	}
	
	public static void main(String[] args) {
		RobotController control = new RobotController(200);
		Sensor sensor = new Sensor();
		float color[]= new float[3];
		
		initializeColor(sensor);
		
		while(true) {
			LCD.clear();
			color = sensor.getColor();
			if(isBlack(color)) {
				control.turnLeft();
			}else if(isWhite(color)){
				control.turnRight();
			}else if(isRed(color)) {
				control.forward();
			}else {
				control.stop();
			}
			LCD.drawString(String.format("%.3f", color[0]), 0, 3);
			LCD.drawString(String.format("%.3f", color[1]), 0, 4);
			LCD.drawString(String.format("%.3f", color[2]), 0, 5);
			Delay.msDelay(20);
		}
	}
	
	private static boolean isBlack(float color[]) {
		if (color[0]<blackTrack[0]*1.1 && 
			color[1]<blackTrack[1]*1.1 &&
			color[2]<blackTrack[2]*1.1 ) {
			return true;
		}else {
			return false;
		}
	}
	
	private static boolean isWhite(float color[]) {
		if (color[0]>whiteTrack[0]*.9 && 
			color[1]>whiteTrack[1]*.9 &&
			color[2]>whiteTrack[2]*.9 ) {
			return true;
		}else {
			return false;
		}
	}
	
	private static boolean isRed(float color[]) {
		if (color[0]>redTrack[0]*.8 && 
			color[1]>redTrack[1]*.8 && color[1]<redTrack[1]*1.2 &&
			color[2]>redTrack[2]*.8 && color[2]<redTrack[2]*1.2) {
			return true;
		}else {
			return false;
		}
	}
}

/* 
 * Ref Couleur
 * Rouge : 0.224; 0.054; 0.023
 * Noir : 0.170; 0.200; 0.150
 * Blanc : 0.290; 0.280; 0.210
 * Rose : 0.122; 0.080 ; 0.130
*/
