package org.team751.robot2015.commands.elevator;

import org.team751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorJoystick extends Command {

	public ElevatorJoystick() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.elevator.grabbing) return;
		Robot.elevator.controller.Set(-Robot.oi.operatorStick.getY());

		if (Robot.oi.operatorStick.getRawButton(7)) {
			RaiseElevator re = new RaiseElevator();
			re.start();
		}
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
}
