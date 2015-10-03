package org.team751.robot2015.commands.drivetrain;

import org.team751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftToPosition extends Command {
	private int	pos;

	public LiftToPosition(int pos) {
		// requires(Robot.elevator);

		this.pos = pos;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevator.heightPIDController.setSetpoint(pos);
		Robot.elevator.heightPIDController.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.elevator.heightPIDController.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevator.heightPIDController.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
