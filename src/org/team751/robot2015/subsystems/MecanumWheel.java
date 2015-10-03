package org.team751.robot2015.subsystems;

import org.team751.robot2015.Constants;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.hal.CanTalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MecanumWheel {
	private String			name;
	public CanTalonSRX		controller;
	public Encoder			encoder;
	public PIDController	pidController;

	public MecanumWheel(String name, int address, int encoderA, int encoderB, double p, double i, double d) {
		this.name = name;
		this.controller = new CanTalonSRX(address);
		this.encoder = new Encoder(encoderA, encoderB);
		this.encoder.setDistancePerPulse(Constants.kDistancePerPulse);

		this.pidController = new PIDController(p, i, d, getSource(), getOutput());
	}

	public void showInSmartDashboard() {
		SmartDashboard.putData(this.name, this.pidController);
	}

	private PIDSource getSource() {
		return new PIDSource() {
			@Override
			public double pidGet() {
				return encoder.getRate();
			}
		};
	}

	private PIDOutput getOutput() {
		return new RateControlledMotor(controller, name);
	}
}
