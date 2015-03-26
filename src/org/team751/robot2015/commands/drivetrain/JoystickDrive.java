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

		// Handle straight drive
		boolean driveStraight = Robot.oi.driveStick.getRawButton(3);
		if (driveStraight) x = 0;

		// Handle disabled FoD
		boolean nonFoD = Robot.oi.driveStick.getRawButton(2);
		if (nonFoD) angle = 0;

		// Handle exponential drive
		if (Constants.kDriveMode == Constants.DriveMode.EXPONENTIAL) {
			y = Math.abs(y) * y;
			x = Math.abs(x) * x;
		}

		// Handle logarithmic drive
		if (Constants.kDriveMode == Constants.DriveMode.LOGARITHMIC) {
			x = logrithmicize(x);
			y = logrithmicize(y);
		}

		if (rotation > Constants.kMinimumAnglePower) {
			Robot.drivetrain.anglePIDController.setSetpoint(Robot.getImu().getYaw());
		} else {
			rotation = Robot.drivetrain.angleOutput.getRotationalPower();
		}

		SmartDashboard.putNumber("Rotational Power", rotation);

		// Call mecanum method
		Robot.drivetrain.mecanum(x, y, rotation, angle);
	}

	// https://www.google.com/#q=(1%2Flog(2))*log(x%2B1.05)
	private double logrithmicize(double val) {
		int multiplier = val > 0 ? 1 : -1;

		val = Math.abs(val);

		double offset = 1.05;
		double k = (1 / Math.log(1 + offset));
		double v = Math.log(val + offset);
		return multiplier * k * v;
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
		} else if (Robot.oi.driveStick.getRawButton(5)) {
			Robot.drivetrain.setSpeedMultiplier(Constants.kSpeedMultiplierC);
		} else {
			Robot.drivetrain.setSpeedMultiplier(Constants.kSpeedMultiplierDefault);
		}
	}
}
