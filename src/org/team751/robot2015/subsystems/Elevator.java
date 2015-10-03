package org.team751.robot2015.subsystems;

import org.team751.robot2015.commands.elevator.ElevatorJoystick;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;

public class Elevator extends Subsystem {
	public DigitalInput		toteSensor	= new DigitalInput(9);
	public CanTalonSRX		controller	= new CanTalonSRX(5);
	public boolean			grabbing	= false;
	public int				position	= 0;
	public PIDController	heightPIDController;

	// public DigitalInput topLimit = new DigitalInput(8);

	public Elevator() {
		heightPIDController = new PIDController(0.2, 0, 0, new PIDSource() {
			@Override
			public double pidGet() {
				return position;
			}
		}, new SpeedControllerOutput() {

			@Override
			public void setSpeed(double speed) {
				controller.Set(speed);
			}
		});

		heightPIDController.setSetpoint(5);
		heightPIDController.setAbsoluteTolerance(1);
		// heightPIDController.enable();

		// JoystickButton b = new JoystickButton(Robot.oi.driveController, 1);
		// b.toggleWhenPressed(new AutoGrab());
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ElevatorJoystick());
	}
}
