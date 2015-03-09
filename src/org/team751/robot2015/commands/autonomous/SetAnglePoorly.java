package org.team751.robot2015.commands.autonomous;

import org.team751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SetAnglePoorly extends Command {
	double	speed;
	double	angle;

	public SetAnglePoorly(double speed, double angle) {
		requires(Robot.drivetrain);

		this.speed = speed;
		this.angle = angle;
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

		SmartDashboard.putNumber("IMU", Robot.getImu().getYaw());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Math.abs(angle - (Robot.getImu().getYaw())) < 3;
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

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

	}
}
