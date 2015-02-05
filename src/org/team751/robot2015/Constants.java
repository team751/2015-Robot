package org.team751.robot2015;

public class Constants {
	// Drivetrain
	public static final double	kMinSpeed							= .1;
	public static final double	kDistancePerPulse					= .02;
	public static final double	kAngleJoystickMultiplier			= .25;
	public static final double	kSpeedMultiplierDefault				= 30.0;
	public static final double	kSpeedMultiplierA					= 10.0;
	public static final double	kSpeedMultiplierB					= 40.0;
	public static final double	kSpeedMultiplierC					= 60.0;

	public static final boolean	kBarnMecanumEnabled					= false;
	public static final boolean	kExponentialDriveEnabled			= false;
	public static final boolean	kDisableRotationOnStraightStrafe	= false;

	private Constants() {
	}
}
