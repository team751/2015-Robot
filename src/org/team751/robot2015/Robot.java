package org.team751.robot2015;

import org.team751.robot2015.commands.drivetrain.Autonomous;
import org.team751.robot2015.commands.grabber.JoystickGrabber;
import org.team751.robot2015.subsystems.Drivetrain;
import org.team751.robot2015.subsystems.Elevator;
import org.team751.robot2015.subsystems.Grabber;
import org.team751.robot2015.utils.lighting.Lighting;
import org.team751.robot2015.utils.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	public static Drivetrain	drivetrain;
	public static Elevator		elevator;
	public static Grabber		mobileGrabber;
	public static Grabber		fixedGrabber;
	public static OI			oi;

	// IMU
	private static SerialPort	serial_port;
	private static IMUAdvanced	imu;

	Command						autonomousCommand;
	public static Command		joystickGrabber;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		setupIMU();

		drivetrain = new Drivetrain();
		elevator = new Elevator();
		mobileGrabber = new Grabber(RobotMap.kMobileGrabberPWM, RobotMap.kMobileGrabberPotentiometerInput, 0.35, 0.0, 0.08);
		fixedGrabber = new Grabber(RobotMap.kFixedGrabberPWM, RobotMap.kFixedGrabberPotentiometerInput, 0.6, 0.004, 0.07);

		joystickGrabber = new JoystickGrabber();

		autonomousCommand = new Autonomous();

		oi = new OI();

		Robot.getImu().zeroYaw();

		Lighting.setColor(Lighting.LEDColor.WHITE);
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();

		updateLighting(true);
	}

	public void autonomousInit() {
		Robot.getImu().zeroYaw();

		autonomousCommand.start();

		Lighting.setColor(Lighting.LEDColor.PURPLE, true);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		if (autonomousCommand != null) autonomousCommand.cancel();

		drivetrain.leftFront.pidController.enable();
		drivetrain.rightFront.pidController.enable();
		drivetrain.rightRear.pidController.enable();
		drivetrain.leftRear.pidController.enable();

		joystickGrabber.start();

		// if (!DriverStation.getInstance().isFMSAttached())
		Lighting.setColor(Lighting.LEDColor.GREEN);
		// else
		// updateLighting();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {
		drivetrain.leftFront.pidController.disable();
		drivetrain.rightFront.pidController.disable();
		drivetrain.rightRear.pidController.disable();
		drivetrain.leftRear.pidController.disable();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		SmartDashboard.putNumber("Fixed Grabber Potentiometer NV", fixedGrabber.potentiometer.getAverageValue() / 100.0);
		SmartDashboard.putNumber("Mobile Grabber Potentiometer NV", mobileGrabber.potentiometer.getAverageValue() / 100.0);
		SmartDashboard.putData("Mobile Grabber PID", mobileGrabber.pidController);
		SmartDashboard.putData("Fixed Grabber PID", fixedGrabber.pidController);
		SmartDashboard.putNumber("grabFIX", fixedGrabber.controller.get());
		SmartDashboard.putNumber("grabMOB", mobileGrabber.controller.get());

		if (!fixedGrabber.pidController.onTarget() || !mobileGrabber.pidController.onTarget()) {
			Lighting.setColor(Lighting.LEDColor.YELLOW);
		} else {
			Lighting.setColor(Lighting.LEDColor.GREEN);
		}

		SmartDashboard.putNumber("IMU", Robot.getImu().getYaw());

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	private void updateLighting(boolean override) {
		if (!DriverStation.getInstance().isDSAttached())
			Lighting.setColor(Lighting.LEDColor.WHITE, override);
		else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue)
			Lighting.setColor(Lighting.LEDColor.BLUE, override);
		else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red)
			Lighting.setColor(Lighting.LEDColor.RED, override);
		else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Invalid) Lighting.setColor(Lighting.LEDColor.WHITE, override);
	}

	private static void setupIMU() {
		try {
			serial_port = new SerialPort(57600, SerialPort.Port.kUSB);

			byte update_rate_hz = 50;
			setImu(new IMUAdvanced(serial_port, update_rate_hz));
		} catch (Exception ex) {

		}
		if (getImu() != null) {
			LiveWindow.addSensor("IMU", "Gyro", getImu());
		}
	}

	public static IMUAdvanced getImu() {
		if (imu == null) Robot.setupIMU();
		return imu;
	}

	public static void setImu(IMUAdvanced imu) {
		Robot.imu = imu;
	}
}
