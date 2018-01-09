import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import network.BroadcastListener;
import network.BroadcastManager;
import network.BroadcastReceiver;

public class Communication implements BroadcastListener{

	BroadcastManager manager;
	BroadcastReceiver receiver;
	ByteBuffer buffer = ByteBuffer.allocate(8);
	boolean state = true;
	int value = 0;
	
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
	
	@Override
	public void onBroadcastReceived(byte[] message) {
		buffer = ByteBuffer.wrap(message);
		value = buffer.getInt();
		state = false;
		LCD.drawString(String.format("%d", value  ), 6, 1);
	}
	
	public void waitData() {
		state = true;
		while(state) {
			Delay.msDelay(20);
		}
		state = true;
	}
	
}
