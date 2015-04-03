package org.team751.robot2015.subsystems;

import org.team751.robot2015.utils.nav6.frc.IMU;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class AnglePID {
	public static class GyroPIDSource implements PIDSource {
		IMU	imu;

		public GyroPIDSource(IMU imu) {
			this.imu = imu;
		}

		@Override
		public double pidGet() {
			if (this.imu != null) return (int) this.imu.getYaw() / 2;
			return 0;
		}
	}

	public static class GyroPIDOutput implements PIDOutput {
		double	rotationalPower;

		public double getRotationalPower() {
			return rotationalPower;
		}

		@Override
		public void pidWrite(double output) {
			this.rotationalPower = output;
		}
	}
}
