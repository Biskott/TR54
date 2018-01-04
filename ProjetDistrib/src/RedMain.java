
public class RedMain {

	/**
	 * Fonction principale du robot de type rouge
	 * @param args
	 */
	public static void main(String[] args) {
		
		RedRobot red = new RedRobot(true);
		red.communication.waitData();
		red.run();
	}
}
