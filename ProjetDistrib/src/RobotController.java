
import lejos.hardware.motor.*;
import lejos.hardware.port.MotorPort;

/**
 * Robot's controller
 * @author Basile BRENON
 */
public class RobotController {

	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;

	float speed;

	/**
	 * Constructor
	 * @param s : default speed in deg/s
	 */
	public RobotController(float s ) {
		speed = s;
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
	}

	/**
	 * Move forward
	 */
	public void forward() {
		leftMotor.forward();
		rightMotor.forward();
	}

	/**
	 * Rotate
	 * @param angle in radians
	 */
	public void rotate (float angle) {
		int angleMotor = (int) (Math.toDegrees(angle)*2.1);
		leftMotor.rotate(angleMotor, true);
		rightMotor.rotate(-angleMotor);
	}
	
	public void rotateLeft () {
		rightMotor.forward();
		leftMotor.backward();
	}
	
	public void rotateRight () {
		rightMotor.backward();
		leftMotor.forward();
	}
	
	public void turnLeft() {
		rightMotor.forward();
		leftMotor.stop();
	}
	
	public void turnRight() {
		leftMotor.forward();
		rightMotor.stop();
	}

	/**
	 * Set the speed of the robot
	 * @param s : speed in deg/s
	 */
	public void setSpeed (float s) {
		leftMotor.setSpeed(s);
		rightMotor.setSpeed(s);
	}

	/**
	 * Reset the speed
	 */
	public void resetSpeed() {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
	}

	/**
	 * Stop the robot
	 */
	public void stop() {
		leftMotor.stop(true);
		rightMotor.stop();
	}

}