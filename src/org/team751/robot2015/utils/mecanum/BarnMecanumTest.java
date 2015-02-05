package org.team751.robot2015.utils.mecanum;

public class BarnMecanumTest {

	public static void main(String[] args) {
		BarnMecanumOutput out = BarnMecanum.mecanum(-0.01, -1, 0, 0);

		System.out.println("Left Front : " + out.leftFrontSpeed);
		System.out.println("Right Front: " + out.rightFrontSpeed);
		System.out.println("Left Rear  : " + out.leftRearSpeed);
		System.out.println("Right Rear : " + out.rightRearSpeed);
	}
}
