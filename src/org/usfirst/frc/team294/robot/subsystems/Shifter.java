package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shifter extends Subsystem {
    
	DoubleSolenoid shifterPiston = new DoubleSolenoid(RobotMap.kSOL_shifterPiston_forward, RobotMap.kSOL_shifterPiston_reverse);
	
	Solenoid shiftLight = new Solenoid(RobotMap.kSOL_shiftLight);

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
    
	public void shiftDown() {
		shifterPiston.set(DoubleSolenoid.Value.kReverse);
		shiftLight.set(true);
	}
	
	public void shiftUp() {
		shifterPiston.set(DoubleSolenoid.Value.kForward);
		shiftLight.set(false);
	}
}

