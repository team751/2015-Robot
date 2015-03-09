package org.team751.robot2015.commands.autonomous;

import org.team751.robot2015.Constants;
import org.team751.robot2015.commands.grabber.SetLowerGrabberPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ThreeToteAuto extends CommandGroup {

	public ThreeToteAuto() {
		addSequential(new SetAnglePoorly(-5, -90));

		grabTote(1);
		grabTote(2);
		grabTote(3);

		// Prep to fly
		addSequential(new SetAnglePoorly(5, 0));

		// Fly away
		addSequential(new DriveForTime(3.0, 15));
	}

	public void grabTote(int t) {
		// Go forwards
		addSequential(new DriveForTime(1.0, 15));

		// Get ready for recycling can smackdown
		addSequential(new SetAnglePoorly(-5, -135));

		// Strafe
		addSequential(new FieldOrientedForwardsForTime(1.0, .6));

		// Rotate back to auto orientation
		addSequential(new SetAnglePoorly(5, -90));

		// Lift
		addSequential(new SetLiftSpeed(0.7));

		// Wait
		addSequential(new WaitForTime(1.0));

		// Unlift
		addSequential(new SetLiftSpeed(0.1 + (t * .8)));

		// Open Grabber
		addSequential(new SetLowerGrabberPosition(Constants.kFullWidthSetpoint));

		// Go Down
		addSequential(new SetLiftSpeed(-.15));

		// Wait
		addSequential(new WaitForTime(1.3));

		// Unlift
		addSequential(new SetLiftSpeed(0.1 + (t * .8)));

		// Drive Forwards
		addSequential(new DriveForTime(1, 10));

		// Grip Tote
		addSequential(new SetLowerGrabberPosition(Constants.kShortToteSetpoint));
	}
}
