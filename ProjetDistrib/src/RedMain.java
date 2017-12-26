
public class RedMain {

public static void main(String[] args) {
		
		RedRobot red = new RedRobot(true, 0.35f, 0.2f, 0.3f, 0.2f, 0.15f);
		red.communication.waitData();
		red.run();
	}
}
