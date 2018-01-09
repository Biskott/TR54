
import lejos.hardware.motor.*;
import lejos.hardware.port.MotorPort;

/**
 * Handle robot's motors
 */
public class RobotController {

	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;

	private float speed;

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
	 * Rotate to a defined angle
	 * @param angle rotation angle in radians
	 */
	public void rotate (float angle) {
		int angleMotor = (int) (Math.toDegrees(angle)*2.1);
		leftMotor.rotate(angleMotor, true);
		rightMotor.rotate(-angleMotor);
	}

	/**
	 * Rotate left while any new command is send
	 */
	public void rotateLeft () {
		rightMotor.forward();
		leftMotor.backward();
	}

	/**
	 * Rotate right while any new command is send
	 */
	public void rotateRight () {
		rightMotor.backward();
		leftMotor.forward();
	}

	/**
	 * Turn left while any new command is send
	 */
	public void turnLeft() {
		rightMotor.forward();
		leftMotor.stop();
	}

	/**
	 * Turn right while any new command is send
	 */
	public void turnRight() {
		leftMotor.forward();
		rightMotor.stop();
	}

	/**
	 * Set new robot speed
	 * @param s new speed in deg/s
	 */
	public void setSpeed (float s) {
		leftMotor.setSpeed(s);
		rightMotor.setSpeed(s);
	}

	/**
	 * Reset the robot's speed to default speed
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
