package org.team751.robot2015.commands.autonomous;

import org.team751.robot2015.commands.ZeroGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoToteAuto extends CommandGroup {
	public TwoToteAuto() {
		addSequential(new SetAngleWell(90)); // 2 s
		addSequential(new ZeroGyro());
		addSequential(new DriveForTime(1.5, .4)); // 6.5
		// addSequential(new DriveToLimit()); // 3 s
		addSequential(new SetLiftSpeed(.5));
		addSequential(new WaitForTime(2)); // 5
		addSequential(new SetLiftSpeed(.1));
		addSequential(new DriveForTime(1.5, -.4)); // 6.5

		addSequential(new DriveForTime(.5, -.4)); // 7
		addSequential(new WaitForTime(.5)); // 7.5
		addSequential(new SetLiftSpeed(0));
		addSequential(new DriveToLimit()); // 8
		addSequential(new SetLiftSpeed(.5));
		addSequential(new WaitForTime(1)); // 9
		addSequential(new DriveForTime(2, -.4)); // 11
	}
}
