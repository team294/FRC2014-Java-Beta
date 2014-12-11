package org.usfirst.frc.team294.robot.subsystems;


import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.util.MultiCANTalon;
import org.usfirst.frc.team294.robot.util.SwitchSpeedController;
//import edu.wpi.first.wpilibj.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
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

	int[] kickerMotors = {RobotMap.kPWM_topKickerMotor, RobotMap.kPWM_bottomKickerMotor};
	SpeedController kickerMotorUnlimited = new MultiCANTalon(kickerMotors);
	SpeedController kickerMotor = new SwitchSpeedController(kickerMotorUnlimited, kickerLimitBack);
	Solenoid kickerPiston = new Solenoid(RobotMap.kSOL_kickerPistonModule, RobotMap.kSOL_kickerPiston);
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	private boolean isReady=true;
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}

	public synchronized void back(){
		
	 	System.out.println(kickerLimitBack.get());
		kickerMotorUnlimited.set(1);
	}
	public synchronized void stop(){
		kickerMotorUnlimited.set(0);
	}
	public synchronized void engage(){
		kickerPiston.set(false);
	}
	public synchronized void disengage(){
		
		kickerPiston.set(true);
		System.out.println("disengage");

	}

	public synchronized boolean isBack(){
		
		return !kickerLimitBack.get();
	}

	public synchronized void setReady(boolean a){
		isReady=a;
	}
	public synchronized boolean getReady(){
		return isReady;
	}
}
