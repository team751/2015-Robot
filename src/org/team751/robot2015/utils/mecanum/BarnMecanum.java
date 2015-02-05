package org.team751.robot2015.utils.mecanum;

// Based on paper here: http://thinktank.wpi.edu/resources/346/ControllingMecanumDrive.pdf
public final class BarnMecanum {
	enum WheelLocation {
		LEFT_FRONT, RIGHT_FRONT, LEFT_REAR, RIGHT_REAR
	}

	// Have a private constructor to prevent initialization
	private BarnMecanum() {
	}

	public static BarnMecanumOutput mecanum(double x, double y, double rotationalSpeed, double gyroAngle) {
		// Calculate the speed from the x and y values with a max of 1
		double speed = distanceFromCoordinates(x, y, 1);

		if (Math.abs(x) > Math.abs(y)) {
			if (x < 0) speed *= -1;
		} else {
			if (y < 0) speed *= -1;
		}

		// Calculate the angle to move at
		double angle = angleFromCoordinates(x, y);

		// Factor the gyro into the angle
		// TODO: check if the sign should be flipped
		double absAngle = angle - gyroAngle;

		return mecanum(speed, absAngle, rotationalSpeed);
	}

	public static BarnMecanumOutput mecanum(double speed, double angle, double rotationalSpeed) {
		// Calculate each wheel's speed (unscaled)
		double unscaledLeftFrontSpeed = unscaledForceVectorForWheel(WheelLocation.LEFT_FRONT, angle) + rotationalSpeed;
		double unscaledRightFrontSpeed = unscaledForceVectorForWheel(WheelLocation.RIGHT_FRONT, angle) - rotationalSpeed;
		double unscaledLeftRearSpeed = unscaledForceVectorForWheel(WheelLocation.LEFT_REAR, angle) + rotationalSpeed;
		double unscaledRightRearSpeed = unscaledForceVectorForWheel(WheelLocation.RIGHT_REAR, angle) - rotationalSpeed;

		// Find the largest speed
		double[] speeds = { unscaledLeftFrontSpeed, unscaledRightFrontSpeed, unscaledLeftRearSpeed, unscaledRightRearSpeed };
		double largestSpeed = -1;
		for (int idx = 0; idx < speeds.length; idx++) {
			if (Math.abs(speeds[idx]) > largestSpeed) largestSpeed = speeds[idx];
		}

		// Scale all the speeds by the largest speed then scale it by the
		// desired speed
		double scaledLeftFrontSpeed = (unscaledLeftFrontSpeed / largestSpeed) * speed;
		double scaledRightFrontSpeed = (unscaledRightFrontSpeed / largestSpeed) * speed;
		double scaledLeftRearSpeed = (unscaledLeftRearSpeed / largestSpeed) * speed;
		double scaledRightRearSpeed = (unscaledRightRearSpeed / largestSpeed) * speed;

		return new BarnMecanumOutput(scaledLeftFrontSpeed, scaledRightFrontSpeed, scaledLeftRearSpeed, scaledRightRearSpeed);
	}

	private static double unscaledForceVectorForWheel(WheelLocation location, double angle) {
		switch (location) {
		case LEFT_FRONT:
			return Math.sin(angle + (Math.PI / 4));
		case RIGHT_FRONT:
			return Math.cos(angle + (Math.PI / 4));
		case LEFT_REAR:
			return Math.cos(angle + (Math.PI / 4));
		case RIGHT_REAR:
			return Math.sin(angle + (Math.PI / 4));
		default:
			return 0.0;
		}
	}

	private static double distanceFromCoordinates(double x, double y, double range) {
		// Square x and y
		double xsq = x * x;
		double ysq = y * y;

		// Square root the sum of the x^2 and y^2
		double distance = Math.sqrt(xsq + ysq);

		// The the range is equal to 0, or the distance is within the range,
		// just return the distance as is
		if (range == 0 || Math.abs(distance) < range) return distance;

		// Otherwise, chomp it then return
		if (Math.abs(distance) > range) {
			if (distance > 0) distance = range;
			if (distance < 0) distance = -range;
		}

		return distance;
	}

	private static double angleFromCoordinates(double x, double y) {
		if (x == 0 && y == 0) return 0;

		if (x >= 0 && y >= 0) return Math.atan(x / y);
		if (x < 0 && y >= 0) return Math.atan(x / y);
		if (x < 0 && y < 0) return -(Math.atan(y / x) + (Math.PI / 2.0));
		if (x >= 0 && y < 0) return -(Math.atan(y / x) - (Math.PI / 2.0));

		return 0;
	}
}
