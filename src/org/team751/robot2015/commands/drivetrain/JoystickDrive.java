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
		// double x = Robot.oi.driveStick.getX();
		// double y = Robot.oi.driveStick.getY();

		double x = Robot.oi.driveController.getRawAxis(4);
		double y = Robot.oi.driveController.getRawAxis(5);

		// x += .068;
		// y += .068;
		// if (x > 0 && x < 0.05) x = 0;
		// if (x < 0 && x > -0.05) x = 0;
		// if (y > 0 && y < 0.05) y = 0;
		// if (y < 0 && y > -0.05) y = 0;

		// double rotation = Robot.oi.driveStick2.getX() *
		// Constants.kAngleJoystickMultiplier;
		double rotation = Robot.oi.driveController.getRawAxis(0) * Constants.kAngleJoystickMultiplier;
		double angle = 0.0;

		// Handle gyro
		if (Robot.getImu() != null && Constants.kFieldOrientedDriveEnabled) angle = Robot.getImu().getYaw();

		// Handle straight strafe mode
		// boolean straightStrafe = Robot.oi.driveStick.getRawButton(1);
		boolean straightStrafe = Robot.oi.driveController.getRawAxis(2) > .5;
		if (straightStrafe) y = 0;

		// Handle straight drive
		// boolean driveStraight = Robot.oi.driveStick.getRawButton(3);
		boolean driveStraight = Robot.oi.driveController.getRawAxis(3) > .5;
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

		// Handle Limit Switches
		// if (Robot.drivetrain.leftLimitSwitch.get() &&
		// Robot.drivetrain.rightLimitSwitch.get() &&
		// !Robot.oi.driveStick.getRawButton(10)) {
		// y = Math.min(0, y);
		// }

		if (rotation > Constants.kMinimumAnglePower || rotation < -Constants.kMinimumAnglePower) {
			Robot.drivetrain.anglePIDController.setSetpoint((int) (Robot.getImu().getYaw() / 2));
			// Robot.drivetrain.anglePIDController.enable();
		} else {
			rotation = Robot.drivetrain.angleOutput.getRotationalPower();
		}

		SmartDashboard.putNumber("Rotational Power", rotation);

		if (x > 0 && x < 0.05) x = 0;
		if (x < 0 && x > -0.05) x = 0;
		if (y > 0 && y < 0.05) y = 0;
		if (y < 0 && y > -0.05) y = 0;

		// x *= -1;
		// y *= -1;

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
