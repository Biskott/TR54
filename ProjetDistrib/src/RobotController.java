
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


//ma version écrite mais déjà en englais vu que le prof demande un code source en anglais de meme que les commentaire



import lejos.robotics.RegulatedMotor;

public class Pilot {
	
	private final RegulatedMotor leftMotor;
	private final RegulatedMotor rightMotor;
	private float forwardSpeed;
	
	
	/*private final float wheelDiameter;
	private final float wheelBase;*/
	
	/**
	 * @param leftMotor
	 * @param rightMotor
	 * @param wheelDiameter
	 * @param wheelBase
	 */
	public Pilot(RegulatedMotor leftMotor, RegulatedMotor rightMotor, float speed) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.setSpeed(speed);
	}
	
	/**
	 * Makes the robot goes forward in straight line
	 */
	public void forward() {
		// Synchronize left and right motors
		//this.leftMotor.synchronizeWith(new RegulatedMotor[] { this.rightMotor });
		// All motors method called between startSynchronization and endSynchronization will be called
		// simultaneously when endSynchronization is called
		//this.leftMotor.startSynchronization();
		this.leftMotor.forward();
		this.rightMotor.forward();

		//this.leftMotor.endSynchronization();
	}
	
	/**
	 * Stops the robot
	 */
	public void stop() {
		// When immediate return is not set (or set to false), the instruction waits for effective stop of the motor
		// When immediate return is set to true, the instruction is not blocking (i.e. the next instruction
		// will be run without waiting for the motor to be effectively stopped)
		this.leftMotor.stop(true);
		this.rightMotor.stop();
	}

	/**
	 * Make the robot Rotate to the left by setting the speed on motors.
	 * In this case the leftMotor is going faster than rightMotor
	 */
	public void leftRotate()
	{
		this.leftMotor.setSpeed((int)((this.getSpeed()*0.4f)/100f * this.leftMotor.getMaxSpeed()));
		this.rightMotor.setSpeed((int)((this.getSpeed()*0.8f)/100f * this.leftMotor.getMaxSpeed()));
		this.forward();
	}

	/**
	 * In this case rightMotor is going faster than leftMotor
	 */
	public void rightRotate()
	{
		this.leftMotor.setSpeed((int)((this.getSpeed()*0.8f)/100f * this.leftMotor.getMaxSpeed()));
		this.rightMotor.setSpeed((int)((this.getSpeed()*0.4f)/100f * this.leftMotor.getMaxSpeed()));	
		this.forward();
	}
	
	/**
	 * Sets the speed of the robot
	 * @param speed the speed (from 0 for stop, to 100 for max speed)
	 */
	public void setSpeed(float speed) 
	{
		this.forwardSpeed = speed;
		this.leftMotor.setSpeed((int)(speed / 100f * this.leftMotor.getMaxSpeed()));
		this.rightMotor.setSpeed((int)(speed / 100f * this.rightMotor.getMaxSpeed()));
	}
	
	public float getSpeed()
	{
		return this.forwardSpeed;
	}
	
	public RegulatedMotor getLeftMotor() {
		// TODO Auto-generated method stub
		return this.leftMotor;
	}

}
