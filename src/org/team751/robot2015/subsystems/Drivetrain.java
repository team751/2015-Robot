package org.team751.robot2015.subsystems;

import org.team751.robot2015.Constants;
import org.team751.robot2015.RobotMap;
import org.team751.robot2015.commands.drivetrain.JoystickDrive;
import org.team751.robot2015.utils.mecanum.BarnMecanum;
import org.team751.robot2015.utils.mecanum.BarnMecanumOutput;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drivetrain extends Subsystem {
	public MecanumWheel				leftFront;
	public MecanumWheel				rightFront;
	public MecanumWheel				leftRear;
	public MecanumWheel				rightRear;

	public DigitalInput				leftLimitSwitch;
	// public DigitalInput rightLimitSwitch;

	public PIDController			anglePIDController;
	public AnglePID.GyroPIDOutput	angleOutput		= new AnglePID.GyroPIDOutput();

	private RobotDrive				robotDrive;

	private double					speedMultiplier	= Constants.kSpeedMultiplierDefault;

	public Drivetrain() {
		leftFront = new MecanumWheel("Left Front", RobotMap.kFrontLeftCAN, RobotMap.kFrontLeftEncoderA, RobotMap.kFrontLeftEncoderB, .01, .00, .001);
		rightFront = new MecanumWheel("Right Front", RobotMap.kFrontRightCAN, RobotMap.kFrontRightEncoderA, RobotMap.kFrontRightEncoderB, 0.01, .00, .001);
		leftRear = new MecanumWheel("Left Rear", RobotMap.kRearLeftCAN, RobotMap.kRearLeftEncoderA, RobotMap.kRearLeftEncoderB, 0.01, .00, .001);
		rightRear = new MecanumWheel("Right Rear", RobotMap.kRearRightCAN, RobotMap.kRearRightEncoderA, RobotMap.kRearRightEncoderB, 0.01, .00, .001);

		leftLimitSwitch = new DigitalInput(8);
		// rightLimitSwitch = new DigitalInput(9);

		if (!Constants.kBarnMecanumEnabled) setupRobotDrive();
	}

	public void mecanum(double x, double y, double rotation) {
		robotDrive.mecanumDrive_Cartesian(x, y, rotation, 0.0);
	}

	public void mecanum(double x, double y, double rotation, double angle) {
		mecanum(x, y, rotation, angle, false);
	}

	public void mecanum(double x, double y, double rotation, double angle, boolean override) {
		if (Constants.kBarnMecanumEnabled) {
			barnMecanum(x, y, rotation, angle);
		} else {
			robotDrive.mecanumDrive_Cartesian(x, y, rotation, angle);
		}

		updateSmartDashboard(x, y, rotation, angle);
	}

	public void barnMecanum(double x, double y, double rotation, double angle) {
		BarnMecanumOutput output = BarnMecanum.mecanum(x, y, rotation, angle);
		leftFront.pidController.setSetpoint(getSpeedMultiplier() * output.leftFrontSpeed);
		leftRear.pidController.setSetpoint(getSpeedMultiplier() * output.leftRearSpeed);
		rightFront.pidController.setSetpoint(-getSpeedMultiplier() * output.rightFrontSpeed);
		rightRear.pidController.setSetpoint(-getSpeedMultiplier() * output.rightRearSpeed);
	}

	public void updateSmartDashboard(double x, double y, double rotation, double angle) {
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

	private void setupRobotDrive() {
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
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new JoystickDrive());
	}
}