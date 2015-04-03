package org.team751.robot2015.utils.position_server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PositionServerSetupUtility implements Runnable {

	@Override
	public void run() {

		while (!PositionServer.hasRecievedMessage) {
			SmartDashboard.putBoolean("RECIEVED", false);
			try {
				DatagramSocket socket = new DatagramSocket();
				byte[] buf = new byte[256];
				buf = "setup".getBytes();

				InetAddress ip = InetAddress.getByName("10.7.51.43");

				DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, 9000);
				socket.send(packet);
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		SmartDashboard.putBoolean("RECIEVED", true);
	}

}
