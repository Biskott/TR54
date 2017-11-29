import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class BlueRobot {

	private static float blueTrack[], blackTrack[], whiteTrack[] = new float[3];
	
	private static void initializeColor(Sensor s) {
		LCD.drawString("Put on the blue",1, 3);
		LCD.drawString("track and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		blueTrack=s.getColor();
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
		LCD.clear();
	}
	
	public static void main(String[] args) {
		RobotController control = new RobotController(200);
		Sensor sensor = new Sensor();
		float color[]= new float[3];
		sensor.getColor();
		
		initializeColor(sensor);
		
		while(true) {
			color = sensor.getColor();
			if(isBlack(color)) {
				LCD.drawString("Noir  ", 0, 1);
				//control.turnRight();
			}else if(isWhite(color)){
				LCD.drawString("Blanc ", 0, 1);
				//control.turnLeft();
			}else if(isBlue(color)) {
				LCD.drawString("Bleu  ", 0, 1);
				//control.forward();
			}else {
				LCD.drawString("Rien  ", 0, 1);
				//control.stop();
			}
			LCD.drawString(String.format("%.3f", color[0]), 0, 3);
			LCD.drawString(String.format("%.3f", color[1]), 0, 4);
			LCD.drawString(String.format("%.3f", color[2]), 0, 5);
			Delay.msDelay(20);
		}
	}
	
	static float fuzzy = 0.15f;
	
	private static boolean isBlack(float color[]) {
		if (color[0]<blackTrack[0]*(1+fuzzy) && color[0]>blackTrack[0]*(1-fuzzy) && 
			color[1]<blackTrack[1]*(1+fuzzy) && color[1]>blackTrack[1]*(1-fuzzy) && 
			color[2]<blackTrack[2]*(1+fuzzy) && color[2]>blackTrack[2]*(1-fuzzy) ) {
			return true;
		}else {
			return false;
		}
	}
	
	private static boolean isWhite(float color[]) {
		if (color[0]<whiteTrack[0]*(1+fuzzy) && color[0]>whiteTrack[0]*(1-fuzzy) &&
			color[1]<whiteTrack[1]*(1+fuzzy) && color[1]>whiteTrack[1]*(1-fuzzy) &&
			color[2]<whiteTrack[2]*(1+fuzzy) && color[2]>whiteTrack[2]*(1-fuzzy) ) {
			return true;
		}else {
			return false;
		}
	}
	
	private static boolean isBlue(float color[]) {
		if (color[0]<blueTrack[0]*(1+fuzzy) && color[0]>blueTrack[0]*(1-fuzzy) &&
			color[1]<blueTrack[1]*(1+fuzzy) && color[1]>blueTrack[1]*(1-fuzzy) &&
			color[2]<blueTrack[2]*(1+fuzzy) && color[2]>blueTrack[2]*(1-fuzzy) ) {
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
