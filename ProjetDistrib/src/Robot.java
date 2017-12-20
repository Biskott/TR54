import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public abstract class Robot {
	
	protected RobotController control;
	protected Sensor sensor;

	protected float colorTrack[], blackTrack[], whiteTrack[], yellowMark[], pinkMark[] = new float[3];
	protected boolean direction;
	
	protected float colorFuzzy = 0.3f;
	protected float whiteFuzzy = 0.3f;
	protected float blackFuzzy = 0.3f;
	protected float yellowFuzzy = 0.3f;
	protected float pinkFuzzy = 0.3f;
	
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
	}
	
	public abstract void run();
	
	protected void initializeColor(Sensor s, String colorToFollow) {
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
		LCD.drawString("Put on the pink",1, 3);
		LCD.drawString("marker and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		pinkMark=s.getColor();
		LCD.drawString("Put on the yellow",1, 3);
		LCD.drawString("marker and press",1, 4);
		LCD.drawString("any button",3, 5);
		Button.waitForAnyPress();
		yellowMark=s.getColor();
		LCD.clear();
		LCD.drawString("Loading ...",1, 3);
		Delay.msDelay(4000);
		LCD.clear();
	}
	
	protected boolean isBlack(float color[]) {
		if (color[0]<blackTrack[0]*(1+blackFuzzy) && color[0]>blackTrack[0]*(1-blackFuzzy) && 
			color[1]<blackTrack[1]*(1+blackFuzzy) && color[1]>blackTrack[1]*(1-blackFuzzy) && 
			color[2]<blackTrack[2]*(1+blackFuzzy) && color[2]>blackTrack[2]*(1-blackFuzzy) ) {
			return true;
		}else {
			return false;
		}
	}
	
	protected boolean isWhite(float color[]) {
		if (color[0]<whiteTrack[0]*(1+whiteFuzzy) && color[0]>whiteTrack[0]*(1-whiteFuzzy) &&
			color[1]<whiteTrack[1]*(1+whiteFuzzy) && color[1]>whiteTrack[1]*(1-whiteFuzzy) &&
			color[2]<whiteTrack[2]*(1+whiteFuzzy) && color[2]>whiteTrack[2]*(1-whiteFuzzy) ) {
			return true;
		}else {
			return false;
		}
	}
	
	protected boolean isColorToFollow(float color[]) {
		if (color[0]<colorTrack[0]*(1+colorFuzzy) && color[0]>colorTrack[0]*(1-colorFuzzy) &&
			color[1]<colorTrack[1]*(1+colorFuzzy) && color[1]>colorTrack[1]*(1-colorFuzzy) &&
			color[2]<colorTrack[2]*(1+colorFuzzy) && color[2]>colorTrack[2]*(1-colorFuzzy) ) {
			return true;
		}else {
			return false;
		}
	}
	
	protected boolean isYellow(float color[]) {
		if (color[0]<yellowMark[0]*(1+yellowFuzzy) && color[0]>yellowMark[0]*(1-yellowFuzzy) && 
			color[1]<yellowMark[1]*(1+yellowFuzzy) && color[1]>yellowMark[1]*(1-yellowFuzzy) && 
			color[2]<yellowMark[2]*(1+yellowFuzzy) && color[2]>yellowMark[2]*(1-yellowFuzzy) ) {
			return true;
		}else {
			return false;
		}
	}
	
	protected boolean isPink(float color[]) {
		if (color[0]<pinkMark[0]*(1+pinkFuzzy) && color[0]>pinkMark[0]*(1-pinkFuzzy) && 
			color[1]<pinkMark[1]*(1+pinkFuzzy) && color[1]>pinkMark[1]*(1-pinkFuzzy) && 
			color[2]<pinkMark[2]*(1+pinkFuzzy) && color[2]>pinkMark[2]*(1-pinkFuzzy) ) {
			return true;
		}else {
			return false;
		}
	}
	
	protected void changeDirection() {
		if(direction) control.rotateRight();
		else control.rotateLeft();
		while(!isColorToFollow(sensor.getColor())) {
			Delay.msDelay(10);
		}
		direction = !direction;
		control.stop();
		
	}
}
