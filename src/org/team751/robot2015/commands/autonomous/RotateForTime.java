package org.team751.robot2015.commands.autonomous;

import org.team751.robot2015.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateForTime extends Command {
	Timer	timer;
	double	time;
	double	speed;

	public RotateForTime(double time, double speed) {
		requires(Robot.drivetrain);

		this.time = time;
		this.speed = speed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer = new Timer();
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drivetrain.leftFront.pidController.enable();
		Robot.drivetrain.rightFront.pidController.enable();
		Robot.drivetrain.leftRear.pidController.enable();
		Robot.drivetrain.rightRear.pidController.enable();

		Robot.drivetrain.leftFront.pidController.setSetpoint(-speed);
		Robot.drivetrain.rightFront.pidController.setSetpoint(-speed);
		Robot.drivetrain.leftRear.pidController.setSetpoint(-speed);
		Robot.drivetrain.rightRear.pidController.setSetpoint(-speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timer.get() > time;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.leftFront.pidController.setSetpoint(0);
		Robot.drivetrain.rightFront.pidController.setSetpoint(0);
		Robot.drivetrain.leftRear.pidController.setSetpoint(0);
		Robot.drivetrain.rightRear.pidController.setSetpoint(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
