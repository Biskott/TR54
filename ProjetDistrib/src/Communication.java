import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import network.BroadcastListener;
import network.BroadcastManager;
import network.BroadcastReceiver;

/**
 * Handle communication between robot
 */
public class Communication implements BroadcastListener{

	BroadcastManager manager;
	BroadcastReceiver receiver;
	ByteBuffer buffer = ByteBuffer.allocate(8);
	boolean waiting = true;
	int value = 0;

	/**
	 * Constructor
	 */
	public Communication() {
		try {
			manager = BroadcastManager.getInstance();
			receiver = BroadcastReceiver.getInstance();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		receiver.addListener(this);
	}

	/**
	 * Send integer to everybody on the network (broadcast)
	 * @param i integer to send
	 */
	public void sendInt(int i) {
		buffer.putInt(i);
		try {
			manager.broadcast(buffer.array());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buffer.clear();
	}

	/**
	 * Triggered on broadcast received
	 * Change
	 * @param message the raw message
	 */
	@Override
	public void onBroadcastReceived(byte[] message) {
		buffer = ByteBuffer.wrap(message);
		value = buffer.getInt();
		waiting = false;
		LCD.drawString(String.format("%d", value  ), 6, 1);
	}

	/**
	 * Wait until a data is send
	 * @param message the raw message
	 */
	public void waitData() {
		waiting = true;
		while(waiting) {
			Delay.msDelay(20);
		}
		waiting = true;
	}

}
