package org.team751.robot2015.commands.autonomous;

import org.team751.robot2015.Robot;
import org.team751.robot2015.commands.drivetrain.DriveTilLimitIsPressed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGrab extends CommandGroup {

	public AutoGrab() {
		// addSequential(new LiftToPosition(2));

		addSequential(new DriveTilLimitIsPressed());
		addSequential(new WaitForTime(.5));

		// addSequential(new WaitForTime(.5));
		// addSequential(new SetLiftSpeed(.5));
		// addSequential(new LiftToPosition(12));

		addSequential(new SetLiftSpeed(1));
		addSequential(new WaitForTime(2));
		addSequential(new SetLiftSpeed(0));
		// addSequential(new WaitForTime(2));
		// addSequential(new SetLiftSpeed(0));
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}

	@Override
	protected boolean isFinished() {
		return (super.isFinished() || Robot.oi.driveController.getRawButton(1));
	}
}
