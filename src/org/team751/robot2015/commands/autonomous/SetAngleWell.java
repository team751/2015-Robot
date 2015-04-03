package org.team751.robot2015.commands.autonomous;

import org.team751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetAngleWell extends Command {
	double	angle;

	public SetAngleWell(double angle) {
		this.angle = angle;

		requires(Robot.drivetrain);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.anglePIDController.setSetpoint(this.angle);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double rotation = Robot.drivetrain.angleOutput.getRotationalPower();
		Robot.drivetrain.mecanum(0, 0, rotation);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.drivetrain.anglePIDController.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
