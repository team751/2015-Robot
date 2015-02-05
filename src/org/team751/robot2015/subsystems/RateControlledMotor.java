package org.team751.robot2015.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RateControlledMotor implements PIDOutput {
	private final CanTalonSRX	motor;
	private double				speed	= 0.0;
	private String				name;

	/**
	 * Construct a new instance and associate a speed controller with the
	 * object.
	 *
	 * @param motor
	 *            The speed controller that is controlling the motor.
	 */
	public RateControlledMotor(CanTalonSRX motor, String name) {
		this.motor = motor;
		this.name = name;
	}

	/**
	 * Apply power value computed by PID to the motor.
	 *
	 * <p>
	 * The standard PID system basis the power output on the amount of "error".
	 * This results in the power going to 0 as the error goes to 0. While this
	 * works well for a distance based PID (where you want to stop once you get
	 * to where you are going). It does not work well for a rate system (where
	 * you want to keep spinning at the same rate).
	 * </p>
	 *
	 * <p>
	 * Instead of treating the value passed as a new power level, we treat it as
	 * an adjustment to the current power level when we apply it.
	 * </p>
	 *
	 * @param output
	 *            Power value to apply (computed by PID loop). Goes to zero as
	 *            we reach the desired spin rate.
	 */
	public void pidWrite(double output) {
		// Treat new PID computed power output as an "adjustment"
		double rateOutput = speed + output;

		rateOutput = Math.min(1.0, rateOutput);
		rateOutput = Math.max(-1.0, rateOutput);

		// if (rateOutput > 0 && rateOutput < .15) motor.Set(0.0);
		// if (rateOutput < 0 && rateOutput > -.15) motor.Set(0.0);
		// if (!(rateOutput > -.15 && rateOutput < .15))
		motor.Set(-rateOutput);

		speed = rateOutput;

		SmartDashboard.putNumber(name + " speed", speed);

	}
}
