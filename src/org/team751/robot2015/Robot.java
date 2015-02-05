package org.team751.robot2015;

import org.team751.robot2015.subsystems.Drivetrain;
import org.team751.robot2015.utils.nav6.frc.IMUAdvanced;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Drivetrain	drivetrain;
	public static OI			oi;

	// IMU
	private SerialPort			serial_port;
	public static IMUAdvanced	imu;

	Command						autonomousCommand;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		oi = new OI();
		// autonomousCommand = new ExampleCommand();

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
