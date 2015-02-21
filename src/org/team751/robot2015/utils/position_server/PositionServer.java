package org.team751.robot2015.utils.position_server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PositionServer implements Runnable {
	private final static int	PACKETSIZE	= 100;
	int							PORT		= 8000;
	DatagramSocket				socket;

	public PositionServer() {
		try {
			socket = new DatagramSocket(PORT);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			if (socket == null) return;
			try {
				// Create a packet
				DatagramPacket packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);

				// Receive a packet (blocking)
				socket.receive(packet);

				String data = new String(packet.getData());

				// Discard everything after the semicolon
				String first = data.split(";")[0];

				if (first.charAt(0) == 'C') {
					// It's from the carriage
					String positionString = first.substring(1, first.length());
					int position = Integer.parseInt(positionString);
					// System.out.println(position);
					SmartDashboard.putNumber("position", position);
					// Robot.elevator.position = position;
				}

				// System.out.println(packet.getAddress() + " " +
				// packet.getPort() + ": " + new String(packet.getData()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
