package org.team751.robot2015;

import org.team751.robot2015.commands.drivetrain.Autonomous;
import org.team751.robot2015.commands.grabber.JoystickGrabber;
import org.team751.robot2015.subsystems.Drivetrain;
import org.team751.robot2015.subsystems.Elevator;
import org.team751.robot2015.subsystems.Grabber;
import org.team751.robot2015.utils.lighting.Lighting;
import org.team751.robot2015.utils.nav6.frc.IMUAdvanced;
import org.team751.robot2015.utils.position_server.PositionServer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
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

	public static Drivetrain		drivetrain;
	public static Elevator			elevator;
	public static Grabber			mobileGrabber;
	public static Grabber			fixedGrabber;
	public static OI				oi;
	public static PositionServer	positionServer;

	// IMU
	private SerialPort				serial_port;
	private static IMUAdvanced		imu;

	Command							autonomousCommand	= new Autonomous();
	public static Command			joystickGrabber;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// autonomousCommand = new ExampleCommand();

		drivetrain = new Drivetrain();
		elevator = new Elevator();
		mobileGrabber = new Grabber(RobotMap.kMobileGrabberPWM, RobotMap.kMobileGrabberPotentiometerInput, 0.35, 0.0, 0.08);
		fixedGrabber = new Grabber(RobotMap.kFixedGrabberPWM, RobotMap.kFixedGrabberPotentiometerInput, 0.6, 0.004, 0.07);

		joystickGrabber = new JoystickGrabber();

		oi = new OI();

		setupIMU();

		Robot.getImu().zeroYaw();
		// SmartDashboard.putNumber("mobile grabber speed", .2);

		Lighting.setColor(Lighting.LEDColor.WHITE);

		positionServer = new PositionServer();
		Thread positionServerThread = new Thread(positionServer);
		positionServerThread.start();
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();

		updateLighting();

		// Enumeration e;
		// try {
		// e = NetworkInterface.getNetworkInterfaces();
		// while (e.hasMoreElements()) {
		// NetworkInterface n = (NetworkInterface) e.nextElement();
		// Enumeration ee = n.getInetAddresses();
		// while (ee.hasMoreElements()) {
		// InetAddress i = (InetAddress) ee.nextElement();
		// System.out.println(i.getHostAddress());
		// }
		// }
		// } catch (SocketException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		Robot.getImu().zeroYaw();

		autonomousCommand.start();

		Lighting.setColor(Lighting.LEDColor.PURPLE);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

		// SmartDashboard.putBoolean("LIMIT SWITCH", button.get());
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

		joystickGrabber.start();

		if (!DriverStation.getInstance().isFMSAttached())
			Lighting.setColor(Lighting.LEDColor.GREEN);
		else
			updateLighting();
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

		// elevator.controller.Set(SmartDashboard.getNumber("mobile grabber speed",
		// .3));

		SmartDashboard.putNumber("Fixed Grabber Potentiometer NV", fixedGrabber.potentiometer.getAverageValue() / 100.0);
		SmartDashboard.putNumber("Mobile Grabber Potentiometer NV", mobileGrabber.potentiometer.getAverageValue() / 100.0);
		SmartDashboard.putData("Mobile Grabber PID", mobileGrabber.pidController);
		SmartDashboard.putData("Fixed Grabber PID", fixedGrabber.pidController);
		SmartDashboard.putNumber("grabFIX", fixedGrabber.controller.get());
		SmartDashboard.putNumber("grabMOB", mobileGrabber.controller.get());
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	private void updateLighting() {
		if (!DriverStation.getInstance().isDSAttached())
			Lighting.setColor(Lighting.LEDColor.WHITE);
		else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue)
			Lighting.setColor(Lighting.LEDColor.BLUE);
		else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red)
			Lighting.setColor(Lighting.LEDColor.RED);
		else if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Invalid) Lighting.setColor(Lighting.LEDColor.WHITE);
	}

	private void setupIMU() {
		// if (!Constants.kImuEnabled) return;
		try {
			serial_port = new SerialPort(57600, SerialPort.Port.kUSB);

			// You can add a second parameter to modify the
			// update rate (in hz) from 4 to 100. The default is 100.
			// If you need to minimize CPU load, you can set it to a
			// lower value, as shown here, depending upon your needs.

			byte update_rate_hz = 50;
			setImu(new IMUAdvanced(serial_port, update_rate_hz));
		} catch (Exception ex) {

		}
		if (getImu() != null) {
			LiveWindow.addSensor("IMU", "Gyro", getImu());
		}
	}

	public static IMUAdvanced getImu() {
		return imu;
	}

	public static void setImu(IMUAdvanced imu) {
		Robot.imu = imu;
	}
}
