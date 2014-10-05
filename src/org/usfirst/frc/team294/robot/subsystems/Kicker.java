package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Kicker extends Subsystem {

	Solenoid kickerPiston = new Solenoid(RobotMap.kSOL_kickerPiston);
	
	private final Timer shotTimer = new Timer();
	
	public Kicker() {
		kickerPiston.set(false);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void fire() {
    	shotTimer.reset();
    	shotTimer.start();
    	kickerPiston.set(true);
    }
    
    public void engage() {
    	kickerPiston.set(false);
    }
    
    public double getShotTime() {
    	return shotTimer.get();
    }
    
    public void stopShotTimer() {
    	shotTimer.stop();
    }
}

