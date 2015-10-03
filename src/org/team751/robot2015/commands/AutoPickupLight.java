package org.team751.robot2015.commands;

import org.team751.robot2015.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoPickupLight extends Command {
	Timer	raiseTimer	= new Timer();	// stopwatch

	public AutoPickupLight() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.elevator); // locks subsystem
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		raiseTimer.reset(); // if run autonomous twice, won't call constructor
							// twice so must reinit things
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() { // repeated
		SmartDashboard.putNumber("TIMER", raiseTimer.get());
		SmartDashboard.putBoolean("Tote Sensor", Robot.elevator.toteSensor.get());
		if (raiseTimer.get() == 0) { // if timer is not running
			if (!Robot.elevator.toteSensor.get()) { // if sensor is activated
				raiseTimer.start();
				Robot.elevator.controller.Set(1.0); // set it to go @ 100% speed
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return raiseTimer.get() > 2.0;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevator.controller.Set(0.0); // kill motor & stop timer
		raiseTimer.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
