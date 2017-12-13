import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class BlueRobot extends Robot {
	
	protected BlueRobot(boolean direction, float colorFuzzy, float whiteFuzzy, float blackFuzzy) {
		super(direction, colorFuzzy, whiteFuzzy, blackFuzzy);
		// TODO Auto-generated constructor stub
	}
	
	public void run() {
		
		RobotController control = new RobotController(200);
		Sensor sensor = new Sensor();
		float color[]= new float[3];
		sensor.getColor();
		
		initializeColor(sensor, "blue");
		
		while(true) {
			color = sensor.getColor();
			if(isBlack(color)) {
				LCD.drawString("Noir  ", 0, 1);
				control.turnLeft();
			}else if(isWhite(color)){
				LCD.drawString("Blanc ", 0, 1);
				control.turnRight();
			}else if(isColorToFollow(color)) {
				LCD.drawString("Bleu  ", 0, 1);
				control.forward();
			}else {
				LCD.drawString("Rien  ", 0, 1);
				control.stop();
			}
			LCD.drawString(String.format("%.3f", color[0]), 0, 3);
			LCD.drawString(String.format("%.3f", color[1]), 0, 4);
			LCD.drawString(String.format("%.3f", color[2]), 0, 5);
			Delay.msDelay(20);
		}
	}
}
