package org.team751.robot2015.commands.drivetrain;

import org.team751.robot2015.Constants;
import org.team751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class JoystickDrive extends Command {

	public JoystickDrive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Handle speed multipliers
		updateSpeedMultiplier();

		// Set default values for mecanum
		double x = Robot.oi.driveStick.getX();
		double y = Robot.oi.driveStick.getY();
		double rotation = Robot.oi.driveStick2.getX() * Constants.kAngleJoystickMultiplier;
		double angle = 0.0;

		// Handle gyro
		if (Robot.getImu() != null && Constants.kFieldOrientedDriveEnabled) angle = Robot.getImu().getYaw();

		// Handle straight strafe mode
		boolean straightStrafe = Robot.oi.driveStick.getRawButton(1);
		if (straightStrafe) y = 0;
		if (straightStrafe && Constants.kDisableRotationOnStraightStrafe) rotation = 0.0;

		// Handle disabled FoD
		boolean nonFoD = Robot.oi.driveStick.getRawButton(2);
		if (nonFoD) angle = 0;

		// Handle exponential drive
		if (Constants.kExponentialDriveEnabled) {
			y = Math.abs(y) * y;
			x = Math.abs(x) * x;
		}

		// Call mecanum method
		Robot.drivetrain.mecanum(x, y, rotation, angle);

		// Update SmartDashboard gyro info
		SmartDashboard.putNumber("imu_yaw", angle);
		SmartDashboard.putBoolean("imu_connected", (Robot.getImu() != null));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

	protected void updateSpeedMultiplier() {
		if (Robot.oi.driveStick.getRawButton(4)) {
			Robot.drivetrain.setSpeedMultiplier(Constants.kSpeedMultiplierA);
		} else if (Robot.oi.driveStick.getRawButton(3)) {
			Robot.drivetrain.setSpeedMultiplier(Constants.kSpeedMultiplierB);
		} else if (Robot.oi.driveStick.getRawButton(5)) {
			Robot.drivetrain.setSpeedMultiplier(Constants.kSpeedMultiplierC);
		} else {
			Robot.drivetrain.setSpeedMultiplier(Constants.kSpeedMultiplierDefault);
		}
	}
}
