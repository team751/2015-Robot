package org.team751.robot2015.commands.autonomous;

import org.team751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToLimit extends Command {

	public DriveToLimit() {
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drivetrain.mecanum(0, .5, 0, Robot.getImu().getYaw());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// return Robot.drivetrain.leftLimitSwitch.get() &&
		// Robot.drivetrain.rightLimitSwitch.get();
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.mecanum(0, 0, 0, Robot.getImu().getYaw());
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
