package org.team751.robot2015.subsystems;

import edu.wpi.first.wpilibj.SpeedController;

public abstract class SpeedControllerOutput implements SpeedController {
	private double	speed	= 0.0;

	public abstract void setSpeed(double speed);

	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public double get() {
		return speed;
	}

	@Override
	public void set(double speed, byte syncGroup) {
		set(speed);
	}

	@Override
	public void set(double speed) {
		this.speed = speed;

		setSpeed(speed);
	}

	@Override
	public void disable() {
	}

}
