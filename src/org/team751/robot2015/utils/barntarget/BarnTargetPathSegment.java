package org.team751.robot2015.utils.barntarget;

/**
 * Created by sambaumgarten on 3/26/15
 */
public class BarnTargetPathSegment {
	private final double	heading;
	private final double	xVelocity;
	private final double	yVelocity;
	private final double	xAcceleration;
	private final double	yAcceleration;
	private final double	xPosition;
	private final double	yPosition;
	private final double	elevatorPosition;
	private final boolean	waitForLimitSwitchPress;

	public BarnTargetPathSegment(double heading, double xVelocity, double yVelocity, double xAcceleration, double yAcceleration, double xPosition, double yPosition, double elevatorPosition, boolean waitForLimitSwitchPress) {
		this.heading = heading;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.xAcceleration = xAcceleration;
		this.yAcceleration = yAcceleration;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.elevatorPosition = elevatorPosition;
		this.waitForLimitSwitchPress = waitForLimitSwitchPress;
	}

	public double getHeading() {
		return heading;
	}

	public double getXVelocity() {
		return xVelocity;
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public double getXAcceleration() {
		return xAcceleration;
	}

	public double getYAcceleration() {
		return yAcceleration;
	}

	public double getXPosition() {
		return xPosition;
	}

	public double getYPosition() {
		return yPosition;
	}

	public double getElevatorPosition() {
		return elevatorPosition;
	}

	public boolean shouldWaitForLimitSwitchPress() {
		return waitForLimitSwitchPress;
	}
}
