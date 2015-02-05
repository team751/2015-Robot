package org.team751.robot2015;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// CAN
	public static final int	kFrontLeftCAN		= 1;
	public static final int	kFrontRightCAN		= 2;
	public static final int	kRearLeftCAN		= 3;
	public static final int	kRearRightCAN		= 4;

	// Encoder
	public static final int	kFrontLeftEncoderA	= 0;
	public static final int	kFrontLeftEncoderB	= 1;
	public static final int	kFrontRightEncoderA	= 2;
	public static final int	kFrontRightEncoderB	= 3;
	public static final int	kRearLeftEncoderA	= 4;
	public static final int	kRearLeftEncoderB	= 5;
	public static final int	kRearRightEncoderA	= 6;
	public static final int	kRearRightEncoderB	= 7;
}
