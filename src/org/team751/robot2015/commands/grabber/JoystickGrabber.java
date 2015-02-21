package org.team751.robot2015.commands.grabber;

import org.team751.robot2015.Constants;
import org.team751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickGrabber extends Command {

	private boolean	lastLoopSpeed	= false;

	public JoystickGrabber() {
		requires(Robot.mobileGrabber);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.mobileGrabber.setWidth(Robot.mobileGrabber.getPIDValue());
		Robot.fixedGrabber.setWidth(Robot.fixedGrabber.getPIDValue());
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Handle manual mode
		if (Robot.oi.operatorStick.getRawButton(2)) {
			Robot.elevator.grabbing = true;
			disableActiveGrabbersPID();
			setSpeedOfActiveGrabbers(Robot.oi.operatorStick.getX());
			maintainSetpointOfActiveGrabbers();
			lastLoopSpeed = true;
		} else {
			enableActiveGrabbersPID();
			Robot.elevator.grabbing = false;
			if (lastLoopSpeed) {
				Robot.mobileGrabber.controller.set(0.0);
				Robot.fixedGrabber.controller.set(0.0);
			}
			lastLoopSpeed = false;
		}

		if (Robot.oi.operatorStick.getRawButton(3)) {
			// Middle button
			setWidthOfActiveGrabbers(Constants.kRecyclingBinSetpoint);
		} else if (Robot.oi.operatorStick.getRawButton(4)) {
			// Left button
			setWidthOfActiveGrabbers(Constants.kShortToteSetpoint);
		} else if (Robot.oi.operatorStick.getRawButton(5)) {
			// Right button
			setWidthOfActiveGrabbers(Constants.kLongToteSetpoint);
		} else if (Robot.oi.operatorStick.getRawButton(1)) {
			// Trigger
			setWidthOfActiveGrabbers(Constants.kFullWidthSetpoint);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

	private void setWidthOfActiveGrabbers(double width) {
		if (shouldControlFixedGrabber()) Robot.fixedGrabber.setWidth(width);
		if (shouldControlMobileGrabber()) Robot.mobileGrabber.setWidth(width);
	}

	private void setSpeedOfActiveGrabbers(double speed) {
		if (shouldControlFixedGrabber()) Robot.fixedGrabber.controller.set(speed);
		if (shouldControlMobileGrabber()) Robot.mobileGrabber.controller.set(speed);
	}

	private void maintainSetpointOfActiveGrabbers() {
		if (shouldControlMobileGrabber()) Robot.mobileGrabber.setWidth(Robot.mobileGrabber.getPIDValue());
		if (shouldControlFixedGrabber()) Robot.fixedGrabber.setWidth(Robot.fixedGrabber.getPIDValue());
	}

	private void disableActiveGrabbersPID() {
		if (shouldControlFixedGrabber()) Robot.fixedGrabber.pidController.disable();
		if (shouldControlMobileGrabber()) Robot.mobileGrabber.pidController.disable();
	}

	private void enableActiveGrabbersPID() {
		if (shouldControlFixedGrabber()) Robot.fixedGrabber.pidController.enable();
		if (shouldControlMobileGrabber()) Robot.mobileGrabber.pidController.enable();
	}

	public boolean shouldControlFixedGrabber() {
		return (Robot.oi.operatorStick.getRawButton(11) && Robot.fixedGrabber.isSafe());
	}

	public boolean shouldControlMobileGrabber() {
		return (Robot.oi.operatorStick.getRawButton(10) || !shouldControlFixedGrabber() && Robot.mobileGrabber.isSafe());
	}
}
