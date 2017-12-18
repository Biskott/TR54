import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * Sensors
 * @author Basile BRENON
 */
public class Sensor {

	private EV3UltrasonicSensor usSensor;
	private SampleProvider distSample;

	private EV3ColorSensor colSensor;
	private SensorMode rgbSensor;

	/**
	 * Constructor
	 */
	public Sensor() {

		usSensor = new EV3UltrasonicSensor(SensorPort.S2);
		usSensor.enable();
		distSample = usSensor.getDistanceMode();
		colSensor = new EV3ColorSensor(SensorPort.S3);
		rgbSensor = colSensor.getRGBMode();

	}

	/**
	 * Get the distance
	 * @return distance in meters
	 */
	public float getDistance() {
		float dist[] = new float[1];
		distSample.fetchSample(dist,0);
		return dist[0];
	}

	/**
	 * Get the average distance
	 * @param n : number of sample for the average
	 * @return average value in meters
	 */
	public float getDistance(int n) {
		float moy = 0;
		for(int i=0; i<n; i++) {
			moy += getDistance();
			Delay.msDelay(Math.min(5, 100/n));
		}
		return moy/n;
	}

	/**
	 * Get the color from color sensor
	 * @return float array of RGB value
	 */
	public float[] getColor() {
		float col[] = new float[3];
		rgbSensor.fetchSample(col, 0);
		return col;
	}
}
