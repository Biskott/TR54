
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * Classe Sensor
 * Elle permet la gestion des différents capteurs
 */
public class Sensor {

	// Attributs utilisés pour la gestion du capteur de distance
	private EV3UltrasonicSensor usSensor;
	private SampleProvider distSample;

	// Attributs utilisés pour la gestion du capteur de couleur
	private EV3ColorSensor colSensor;
	private SensorMode rgbSensor;

	/**
	 * Constructeur
	 */
	public Sensor() {

		usSensor = new EV3UltrasonicSensor(SensorPort.S2);
		usSensor.enable();
		distSample = usSensor.getDistanceMode();
		colSensor = new EV3ColorSensor(SensorPort.S3);
		rgbSensor = colSensor.getRGBMode();

	}

	/**
	 * Fonction getDistance
	 * Retourne la distance à un objet détecter par le capteur de distance
	 * @return la distance (en mètres)
	 */
	public float getDistance() {
		float dist[] = new float[1];
		distSample.fetchSample(dist,0);
		return dist[0];
	}

	/**
	 * Fonction getDistance
	 * Retourne la distance à un objet détecter par le capteur de distance
	 * Cette fonction utilise un moyenne de la distance détectée avec les n dernières mesures
	 * @param n : Nombre de valeurs utilisées pour réaliser la moyenne
	 * @return distance moyenne (en mètres)
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
	 * Fonction getColor
	 * Renvoie la couleur détectée par le capteur de couleur
	 * @return tableau de flottant correspondant aux valeurs RGB
	 */
	public float[] getColor() {
		float col[] = new float[3];
		rgbSensor.fetchSample(col, 0);
		return col;
	}
}
