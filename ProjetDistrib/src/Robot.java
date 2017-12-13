import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public abstract class Robot {

	protected float colorTrack[], blackTrack[], whiteTrack[] = new float[3];
	protected boolean direction;
	
	protected float colorFuzzy = 0.3f;
	protected float whiteFuzzy = 0.3f;
	protected float blackFuzzy = 0.3f;
	
	protected Robot(boolean direction, float colorFuzzy, float whiteFuzzy, float blackFuzzy) {
		this.colorFuzzy = colorFuzzy;
		this.whiteFuzzy = whiteFuzzy;
		this.blackFuzzy = blackFuzzy;
		
		this.direction = direction;
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
}
