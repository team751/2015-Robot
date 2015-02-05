package org.team751.robot2015;

import org.team751.robot2015.commands.ExampleCommand;
import org.team751.robot2015.subsystems.Drivetrain;
import org.team751.robot2015.subsystems.ExampleSubsystem;
import org.team751.robot2015.utils.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem	exampleSubsystem	= new ExampleSubsystem();
	public static Drivetrain				drivetrain;
	public static OI						oi;

	// IMU
	private SerialPort						serial_port;
	public static IMUAdvanced				imu;

	Command									autonomousCommand;
	Joystick								joystick			= new Joystick(1);
	Joystick								joystick2			= new Joystick(2);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		oi = new OI();
		// instantiate the command used for the autonomous period
		autonomousCommand = new ExampleCommand();

		drivetrain = new Drivetrain();

		setupIMU();

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null) autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) autonomousCommand.cancel();

		drivetrain.leftFront.pidController.enable();
		drivetrain.rightFront.pidController.enable();
		drivetrain.rightRear.pidController.enable();
		drivetrain.leftRear.pidController.enable();

		SmartDashboard.putData("Left Front PID", drivetrain.leftFront.pidController);
		SmartDashboard.putData("Right Front PID", drivetrain.rightFront.pidController);
		SmartDashboard.putData("Left Rear PID", drivetrain.leftRear.pidController);
		SmartDashboard.putData("Right Rear PID", drivetrain.rightRear.pidController);

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

		// SmartDashboard.putNumber("IMU", this.imu.getYaw());

		SmartDashboard.putNumber("Left Front - ENC", drivetrain.leftFront.encoder.getRate());
		SmartDashboard.putNumber("Right Front - ENC", drivetrain.rightFront.encoder.getRate());
		SmartDashboard.putNumber("Left Rear - ENC", drivetrain.leftRear.encoder.getRate());
		SmartDashboard.putNumber("Right Rear - ENC", drivetrain.rightRear.encoder.getRate());

		// drivetrain.leftFront.pidController.setSetpoint(-20);
		// drivetrain.rightFront.pidController.setSetpoint(20);
		// drivetrain.rightRear.pidController.setSetpoint(20);
		// drivetrain.leftRear.pidController.setSetpoint(-20);

		// drivetrain.rightFront.controller.Set(1.0);
		// drivetrain.leftFront.controller.Set(1.0);
		// drivetrain.rightRear.controller.Set(1.0);
		// drivetrain.leftRear.controller.Set(1.0);

		double x = 0;
		double y = 0;

		if (joystick.getRawButton(4)) {
			drivetrain.setSpeedMultiplier(Constants.kSpeedMultiplierA);
		} else if (joystick.getRawButton(3)) {
			drivetrain.setSpeedMultiplier(Constants.kSpeedMultiplierB);
		} else if (joystick.getRawButton(5)) {
			drivetrain.setSpeedMultiplier(Constants.kSpeedMultiplierC);
		} else {
			drivetrain.setSpeedMultiplier(Constants.kSpeedMultiplierDefault);
		}

		x = joystick.getX();
		y = joystick.getY();

		if (joystick.getRawButton(2)) y = 0;

		if (this.imu != null) {
			drivetrain.mecanum(x, y, joystick2.getX() * .25, this.imu.getYaw());
			SmartDashboard.putBoolean("imu_connected", true);
			SmartDashboard.putNumber("imu_yaw", this.imu.getYaw());
		} else {
			drivetrain.mecanum(x, y, joystick2.getX() * .25, 0);
			SmartDashboard.putBoolean("imu_connected", false);
		}

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	private void setupIMU() {
		// if (!Constants.kImuEnabled) return;
		try {
			serial_port = new SerialPort(57600, SerialPort.Port.kOnboard);

			// You can add a second parameter to modify the
			// update rate (in hz) from 4 to 100. The default is 100.
			// If you need to minimize CPU load, you can set it to a
			// lower value, as shown here, depending upon your needs.

			byte update_rate_hz = 50;
			imu = new IMUAdvanced(serial_port, update_rate_hz);
		} catch (Exception ex) {

		}
		if (imu != null) {
			LiveWindow.addSensor("IMU", "Gyro", imu);
		}
	}
}
