package org.team751.robot2015.commands.drivetrain;

import org.team751.robot2015.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Autonomous extends Command {
	Timer	timer;

	public Autonomous() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer = new Timer();
		timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Robot.drivetrain.barnMecanum(0, .5, 0, 0);
		Robot.drivetrain.leftFront.pidController.enable();
		Robot.drivetrain.rightFront.pidController.enable();
		Robot.drivetrain.leftRear.pidController.enable();
		Robot.drivetrain.rightRear.pidController.enable();

		Robot.drivetrain.leftFront.pidController.setSetpoint(-15);
		Robot.drivetrain.rightFront.pidController.setSetpoint(15);
		Robot.drivetrain.leftRear.pidController.setSetpoint(-15);
		Robot.drivetrain.rightRear.pidController.setSetpoint(15);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return timer.get() > 2.0;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.leftFront.pidController.setSetpoint(0);
		Robot.drivetrain.rightFront.pidController.setSetpoint(0);
		Robot.drivetrain.leftRear.pidController.setSetpoint(0);
		Robot.drivetrain.rightRear.pidController.setSetpoint(0);
		//
		// Robot.drivetrain.leftFront.controller.Set(0);
		// Robot.drivetrain.leftRear.controller.Set(0);
		// Robot.drivetrain.rightRear.controller.Set(0);
		// Robot.drivetrain.rightRear.controller.Set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
