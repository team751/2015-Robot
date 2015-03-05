package org.team751.robot2015.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Grabber extends Subsystem {
	public TalonSRX			controller;
	public AnalogInput		potentiometer;
	public PIDController	pidController;

	private boolean			eStopped	= false;

	public Grabber(int address, int potentiometerChannel, double p, double i, double d) {
		this.controller = new TalonSRX(address);
		this.potentiometer = new AnalogInput(potentiometerChannel);
		this.pidController = new PIDController(p, i, d, getSource(), getOutput());

		// this.potentiometer.setGlobalSampleRate(500);
	}

	public void setWidth(double width) {
		if (eStopped)
			this.pidController.disable();
		else
			this.pidController.setSetpoint(width);
	}

	public double getPIDValue() {
		return potentiometer.getAverageValue() / 100.0;
	}

	private PIDSource getSource() {
		return new PIDSource() {
			@Override
			public double pidGet() {
				return getPIDValue();
			}
		};
	}

	private PIDOutput getOutput() {
		return new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				controller.set(-output);
			}
		};
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(Robot.joystickGrabber);
	}

	public boolean isSafe() {
		return this.potentiometer.getAverageVoltage() > 100.0;
	}

	public void emergencyStop() {
		eStopped = true;
		this.pidController.disable();
		this.controller.set(0.0);
	}

	public void unEmergencyStop() {
		eStopped = false;
	}

	public boolean isEmergencyStopped() {
		return this.eStopped;
	}
}
