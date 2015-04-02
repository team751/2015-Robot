package org.team751.robot2015.commands.drivetrain;

import org.team751.robot2015.Robot;
import org.team751.robot2015.utils.barntarget.BarnTargetPath;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PathFollowerCommand extends Command {
	private Thread			thread;
	private PathFollower	pathFollower;

	public PathFollowerCommand(BarnTargetPath path) {
		pathFollower = new PathFollower(path);
		thread = new Thread(pathFollower);

		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		thread.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return pathFollower.isFinished();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
