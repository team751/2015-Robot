package org.team751.robot2015;

public class Constants {
	// Drivetrain
	public static final double		kMinSpeed					= .025;
	public static final double		kDistancePerPulse			= .02;
	public static final double		kAngleJoystickMultiplier	= .75;
	public static final double		kSpeedMultiplierDefault		= 12;
	public static final double		kSpeedMultiplierA			= 10.0;
	public static final double		kSpeedMultiplierC			= 40.0;

	public static final boolean		kBarnMecanumEnabled			= false;
	public static final boolean		kFieldOrientedDriveEnabled	= true;
	public static final DriveMode	kDriveMode					= DriveMode.PROPORTIONAL;
	// public static final boolean kExponentialDriveEnabled = false;
	// public static final boolean kLogarithmicDriveEnabled = false;
	public static final double		kMinimumAnglePower			= 0.05;

	// Grabber
	public static final double		kLongToteSetpoint			= 7.0;
	public static final double		kShortToteSetpoint			= 10.0;
	public static final double		kRecyclingBinSetpoint		= 6.0;
	public static final double		kFullWidthSetpoint			= 2.5;

	public static enum DriveMode {
		PROPORTIONAL, EXPONENTIAL, LOGARITHMIC,
	}

	private Constants() {
	}
}
