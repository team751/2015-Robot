package org.team751.robot2015.subsystems;

import org.team751.robot2015.commands.elevator.ElevatorJoystick;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;

public class Elevator extends Subsystem {
	public CanTalonSRX	controller	= new CanTalonSRX(5);
	public boolean		grabbing	= false;
	// public int position = 0;

	public DigitalInput	topLimit	= new DigitalInput(8);

	public Elevator() {

	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ElevatorJoystick());
	}
}
