package org.team751.robot2015.commands.autonomous;

import org.team751.robot2015.commands.drivetrain.DriveTilLimitIsPressed;
import org.team751.robot2015.commands.drivetrain.LiftToPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGrabTwo extends CommandGroup {

	public AutoGrabTwo() {
		// Go down
		addSequential(new LiftToPosition(2));
		addSequential(new SetLiftSpeed(-.2));
		addSequential(new WaitForTime(.5));
		addSequential(new SetLiftSpeed(0));

		// Drive
		addSequential(new DriveTilLimitIsPressed());

		// Lift
		addSequential(new WaitForTime(.5));
		addSequential(new LiftToPosition(14));

		// Move forwards
		addSequential(new DriveForTime(1.0, 10));

		// Go Down
		addSequential(new LiftToPosition(8));

		// Move backwards
		addSequential(new DriveForTime(0.25, -10));

		// Go down
		addSequential(new LiftToPosition(2));
		addSequential(new SetLiftSpeed(-.2));
		addSequential(new WaitForTime(.5));
		addSequential(new SetLiftSpeed(0));

		// Drive to tote
		addSequential(new DriveTilLimitIsPressed());

		// Go Up
		addSequential(new WaitForTime(.5));
		addSequential(new LiftToPosition(12));
	}
}
