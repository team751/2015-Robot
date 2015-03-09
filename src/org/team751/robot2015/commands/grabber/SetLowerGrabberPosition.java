package org.team751.robot2015.commands.grabber;

import org.team751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetLowerGrabberPosition extends Command {
	double	width;

	public SetLowerGrabberPosition(double width) {
		requires(Robot.mobileGrabber);

		this.width = width;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.mobileGrabber.setWidth(width);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.mobileGrabber.setWidth(width);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.mobileGrabber.pidController.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
