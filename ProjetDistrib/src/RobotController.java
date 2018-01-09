
import lejos.hardware.motor.*;
import lejos.hardware.port.MotorPort;

/**
 * Classe RobotController
 * Elle permet la gestion des moteurs du robot pour réaliser les déplacements
 */
public class RobotController {

	// Attributs correspondants aux différents moteurs
	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;

	// Attribut correspondant à la vitesse des moteurs
	private float speed;

	/**
	 * Constructeur
	 * @param s : Vitesse par défaut en deg/s
	 */
	public RobotController(float s ) {
		speed = s;
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
	}

	/**
	 * Fonction forward
	 * Permet de faire avancer le robot
	 */
	public void forward() {
		leftMotor.forward();
		rightMotor.forward();
	}

	/**
	 * Fonction rotate
	 * Permet de faire tourner le robot d'un certain angle
	 * @param angle de rotation en radians
	 */
	public void rotate (float angle) {
		int angleMotor = (int) (Math.toDegrees(angle)*2.1);
		leftMotor.rotate(angleMotor, true);
		rightMotor.rotate(-angleMotor);
	}

	/**
	 * Fonction rotateLeft
	 * Elle permet de faire tourner (sur place) le robot sur la gauche (indéfiniment tant qu'il n'y a pas d'autres commandes)
	 */
	public void rotateLeft () {
		rightMotor.forward();
		leftMotor.backward();
	}

	/**
	 * Fonction rotateRight
	 * Elle permet de faire tourner (sur place) le robot sur la droite (indéfiniment tant qu'il n'y a pas d'autres commandes)
	 */
	public void rotateRight () {
		rightMotor.backward();
		leftMotor.forward();
	}

	/**
	 * Fonction turnLeft
	 * Elle permet de faire tourner le robot sur la gauche (indéfiniment tant qu'il n'y a pas d'autres commandes)
	 */
	public void turnLeft() {
		rightMotor.forward();
		leftMotor.stop();
	}

	/**
	 * Fonction turnRight
	 * Elle permet de faire tourner le robot sur la droite (indéfiniment tant qu'il n'y a pas d'autres commandes)
	 */
	public void turnRight() {
		leftMotor.forward();
		rightMotor.stop();
	}

	/**
	 * Fonction setSpeed
	 * Elle permet de fixer la vitesse du robot
	 * @param s : Vitesse en deg/s
	 */
	public void setSpeed (float s) {
		leftMotor.setSpeed(s);
		rightMotor.setSpeed(s);
	}

	/**
	 * Fonction resetSpeed
	 * Permet de remettre la vitesse des moteurs à la valeur par défaut
	 */
	public void resetSpeed() {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
	}

	/**
	 * Fonction stop
	 * Permet d'arrêter le robot
	 */
	public void stop() {
		leftMotor.stop(true);
		rightMotor.stop();
	}

}
