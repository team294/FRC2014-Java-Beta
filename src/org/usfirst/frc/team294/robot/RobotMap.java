package org.usfirst.frc.team294.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// PWM
	public static int kPWM_leftMotor1 = 0;
	public static int kPWM_leftMotor2 = 1;
	public static int kPWM_leftMotor3 = 2;
	public static int kPWM_rightMotor1 = 3;
	public static int kPWM_rightMotor2 = 4;
	public static int kPWM_rightMotor3 = 5;
	public static int kPWM_topKickerMotor = 6;
	public static int kPWM_intakeWheelMotor = 7;
	public static int kPWM_bottomKickerMotor = 8;
	public static int kPWM_pivotMotor = 9;

	// Digital Inputs
	public static int kDIN_leftDriveEncoderA = 0;
	public static int kDIN_leftDriveEncoderB = 1;
	public static int kDIN_rightDriveEncoderA = 2;
	public static int kDIN_rightDriveEncoderB = 3;
	public static int kDIN_kickerLimitBack = 4;
	public static int kDIN_kickerLimitFront = 5;
	public static int kDIN_kickerEncoderA = 6;
	public static int kDIN_kickerEncoderB = 7;
	public static int kDIN_kickerResetLimitSwitch = 8;
	public static int kDIN_buttonIntake = 9;

	// Analog Inputs
	public static int kAIN_gyro = 0;
	public static int kAIN_pivotPot = 1;
	public static int kAIN_rangeFinder = 2;

	// Solenoids
	public static int kSOL_shifterPiston_reverse = 0;
	public static int kSOL_shifterPiston_forward = 1;
	public static int kSOL_jawPiston_reverse = 2;
	public static int kSOL_jawPiston_forward = 3;
	public static int kSOL_kickerPiston = 4;
	public static int kSOL_rangeLight = 5;
	//6
	public static int kSOL_shiftLight = 7;

	public static int kSOL_system_module = 1;
	public static int kSOL_system = 0;
}
