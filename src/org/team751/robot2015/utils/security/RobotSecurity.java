package org.team751.robot2015.utils.security;

import org.team751.robot2015.Robot;

public class RobotSecurity implements Runnable {
	public void checkForSensorDisconnects() {
		if (!Robot.mobileGrabber.isSafe()) Robot.mobileGrabber.emergencyStop();
		if (!Robot.fixedGrabber.isSafe()) Robot.fixedGrabber.emergencyStop();
	}

	@Override
	public void run() {
		while (true) {
			checkForSensorDisconnects();
		}
	}
}
