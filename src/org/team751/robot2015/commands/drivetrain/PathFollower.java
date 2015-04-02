package org.team751.robot2015.commands.drivetrain;

import org.team751.robot2015.Robot;
import org.team751.robot2015.utils.barntarget.BarnTargetPath;
import org.team751.robot2015.utils.barntarget.BarnTargetPathSegment;

public class PathFollower implements Runnable {
	private static double	TIME_PER_STEP	= .01;
	private BarnTargetPath	path;
	private boolean			isFinished		= false;

	public PathFollower(BarnTargetPath path) {
		this.path = path;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		long savedTime = 0;
		while ((System.currentTimeMillis() - startTime) <= path.getSegments().size() * TIME_PER_STEP * 1000 || savedTime != 0) {
			int step = (int) ((System.currentTimeMillis() - startTime) / TIME_PER_STEP);

			BarnTargetPathSegment segment = path.getSegments().get(step);

			if (segment.shouldWaitForLimitSwitchPress()) {
				savedTime = System.currentTimeMillis();

				while (Robot.drivetrain.leftLimitSwitch.get() && Robot.drivetrain.rightLimitSwitch.get()) {
					Robot.drivetrain.mecanum(0, segment.getYVelocity(), 0, 0);
				}

				Robot.drivetrain.mecanum(0, 0, 0, 0);

				startTime += System.currentTimeMillis() - savedTime;

				continue;
			} else if (segment.getElevatorPosition() != -1) {
				// Move Elevator
				continue;
			}

			Robot.drivetrain.anglePIDController.setSetpoint(segment.getHeading());
			Robot.drivetrain.mecanum(segment.getXVelocity(), segment.getYVelocity(), Robot.drivetrain.angleOutput.getRotationalPower(), Robot.getImu().getYaw());
		}

		isFinished = true;
	}

	public boolean isFinished() {
		return isFinished;
	}
}
