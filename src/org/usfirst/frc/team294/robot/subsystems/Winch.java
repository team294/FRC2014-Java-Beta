package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.util.DualSpeedController;
import org.usfirst.frc.team294.robot.util.LinearVictor884;
import org.usfirst.frc.team294.robot.util.SwitchSpeedController;

import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Winch extends Subsystem {

	//Encoder kickerEncoder = new Encoder(RobotMap.kDIN_kickerEncoderA, RobotMap.kDIN_kickerEncoderB);
	DigitalInput kickerLimitBack = new DigitalInput(RobotMap.kDIN_kickerLimitBack);
	//DigitalInput kickerLimitFront = new DigitalInput(RobotMap.kDIN_kickerLimitFront);
	//DigitalInput kickerResetLimitSwitch = new DigitalInput(RobotMap.kDIN_kickerResetLimitSwitch);

	SpeedController topKickerMotor = new LinearVictor884(RobotMap.kPWM_topKickerMotor);
	SpeedController bottomKickerMotor = new LinearVictor884(RobotMap.kPWM_bottomKickerMotor);
	SpeedController kickerMotorUnlimited = new DualSpeedController(topKickerMotor, bottomKickerMotor, (byte) 0);
	SpeedController kickerMotor = new SwitchSpeedController(kickerMotorUnlimited, kickerLimitBack);

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
}

