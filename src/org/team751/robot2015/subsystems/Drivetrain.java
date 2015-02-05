package org.team751.robot2015.subsystems;

import org.team751.robot2015.Constants;
import org.team751.robot2015.RobotMap;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drivetrain extends Subsystem {
	public MecanumWheel	leftFront;
	public MecanumWheel	rightFront;
	public MecanumWheel	leftRear;
	public MecanumWheel	rightRear;

	private RobotDrive	robotDrive;

	private double		speedMultiplier	= Constants.kSpeedMultiplierDefault;

	public Drivetrain() {
		leftFront = new MecanumWheel("Left Front", RobotMap.kFrontLeftCAN, RobotMap.kFrontLeftEncoderA, RobotMap.kFrontLeftEncoderB, .003, .00, .00);
		rightFront = new MecanumWheel("Right Front", RobotMap.kFrontRightCAN, RobotMap.kFrontRightEncoderA, RobotMap.kFrontRightEncoderB, 0.003, .00, .00);
		leftRear = new MecanumWheel("Left Rear", RobotMap.kRearLeftCAN, RobotMap.kRearLeftEncoderA, RobotMap.kRearLeftEncoderB, 0.003, .00, .000);
		rightRear = new MecanumWheel("Right Rear", RobotMap.kRearRightCAN, RobotMap.kRearRightEncoderA, RobotMap.kRearRightEncoderB, 0.003, .00, .00);

		robotDrive = new RobotDrive(new SpeedControllerOutput() {
			@Override
			public void setSpeed(double speed) {
				if ((speed < Constants.kMinSpeed && speed > 0) || (speed > -Constants.kMinSpeed && speed < 0)) {
					leftFront.pidController.setSetpoint(0);
					return;
				}
				leftFront.pidController.setSetpoint(-getSpeedMultiplier() * speed);
				SmartDashboard.putNumber("Left Front speed", speed);
			}
		}, new SpeedControllerOutput() {
			@Override
			public void setSpeed(double speed) {
				if ((speed < Constants.kMinSpeed && speed > 0) || (speed > -Constants.kMinSpeed && speed < 0)) {
					leftRear.pidController.setSetpoint(0);
					return;
				}
				leftRear.pidController.setSetpoint(-getSpeedMultiplier() * speed);
				SmartDashboard.putNumber("Left Rear speed", speed);
			}
		}, new SpeedControllerOutput() {
			@Override
			public void setSpeed(double speed) {
				if ((speed < Constants.kMinSpeed && speed > 0) || (speed > -Constants.kMinSpeed && speed < 0)) {
					rightFront.pidController.setSetpoint(0);
					return;
				}
				rightFront.pidController.setSetpoint(getSpeedMultiplier() * speed);

				SmartDashboard.putNumber("Right Front speed", speed);
			}
		}, new SpeedControllerOutput() {
			@Override
			public void setSpeed(double speed) {
				if ((speed < Constants.kMinSpeed && speed > 0) || (speed > -Constants.kMinSpeed && speed < 0)) {
					rightRear.pidController.setSetpoint(0);
					return;
				}
				rightRear.pidController.setSetpoint(getSpeedMultiplier() * speed);
				SmartDashboard.putNumber("Right Rear speed", speed);
			}
		});

		// robotDrive.setSafetyEnabled(false);
	}

	public void mecanum(double x, double y, double rotation) {
		double angle = 0.0;
		robotDrive.mecanumDrive_Cartesian(x, y, rotation, angle);
	}

	public void mecanum(double x, double y, double rotation, double angle) {
		// BarnMecanumOutput output = BarnMecanum.mecanum(x, y, rotation, 0);
		// leftFront.pidController.setSetpoint(20 * output.leftFrontSpeed);
		// leftRear.pidController.setSetpoint(20 * output.leftRearSpeed);
		// rightFront.pidController.setSetpoint(-20 * output.rightFrontSpeed);
		// rightRear.pidController.setSetpoint(-20 * output.rightRearSpeed);
		robotDrive.mecanumDrive_Cartesian(x, y, rotation, angle);

		SmartDashboard.putData("Left Front PID", leftFront.pidController);
		SmartDashboard.putData("Right Front PID", rightFront.pidController);
		SmartDashboard.putData("Left Rear PID", leftRear.pidController);
		SmartDashboard.putData("Right Rear PID", rightRear.pidController);

		SmartDashboard.putNumber("Left Front - ENC", leftFront.encoder.getRate());
		SmartDashboard.putNumber("Right Front - ENC", rightFront.encoder.getRate());
		SmartDashboard.putNumber("Left Rear - ENC", leftRear.encoder.getRate());
		SmartDashboard.putNumber("Right Rear - ENC", rightRear.encoder.getRate());

		SmartDashboard.putNumber("Mec - X", x);
		SmartDashboard.putNumber("Mec - Y", y);
		SmartDashboard.putNumber("Mec - R", rotation);
		SmartDashboard.putNumber("Mec - A", angle);
	}

	public void setSpeedMultiplier(double speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}

	public double getSpeedMultiplier() {
		return speedMultiplier;
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
