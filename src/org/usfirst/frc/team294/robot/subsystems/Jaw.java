package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Jaw extends Subsystem {

	DoubleSolenoid jawPiston = new DoubleSolenoid(RobotMap.kSOL_jawPiston_forward, RobotMap.kSOL_jawPiston_reverse);
	private boolean jawDown = true;
	
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	public synchronized void open() {
		jawPiston.set(DoubleSolenoid.Value.kForward);
		jawDown = false;
	}

	public synchronized void close() {
		jawPiston.set(DoubleSolenoid.Value.kReverse);
		jawDown = true;
	}
	
	public synchronized boolean isClosed() {
		return jawDown;
	}
	
	public synchronized boolean isOpen() {
		return !jawDown;
	}
}

