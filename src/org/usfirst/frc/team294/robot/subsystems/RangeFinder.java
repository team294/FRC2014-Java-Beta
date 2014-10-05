package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RangeFinder extends Subsystem {

	// 24V solenoid to power range finder sensor
	Solenoid system = new Solenoid(RobotMap.kSOL_system_module, RobotMap.kSOL_system);
	AnalogInput rangeFinder = new AnalogInput(RobotMap.kAIN_rangeFinder);
	Solenoid rangeLight = new Solenoid(RobotMap.kSOL_rangeLight);
	private int[] inRangeHistory = new int[10];

	private class UpdateLightTask implements Runnable {
		private final RangeFinder m_rangeFinder;
		
		public UpdateLightTask(RangeFinder rangeFinder) {
			m_rangeFinder = rangeFinder;
		}
		
		@Override
		public void run() {
			while(true) {
				m_rangeFinder.updateLight();
				Timer.delay(0.04);
			}
		}
	}

	public RangeFinder() {
		system.set(true);
        new Thread(new UpdateLightTask(this)).start();
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	private void updateLight() {
		// determine if we're in range at this sample time
		double v = rangeFinder.getVoltage();
		int newval = 0;
		if (Robot.pivot.isFarShot() && v > 4.7 && v < 6.3)
			newval = 1;
		else if (Robot.pivot.isCloseShot() && v > 2.4 && v < 3.0)
			newval = 1;

		// keep history and add new value
		for (int i=0; i<(inRangeHistory.length-1); ++i)
			inRangeHistory[i] = inRangeHistory[i+1];
		inRangeHistory[inRangeHistory.length-1] = newval;

		// calculate total number of samples in range
		int sum = 0;
		for (int val : inRangeHistory)
			sum += val;
		
		// set light on or off based on threshold
		if (sum >= 7)
			rangeLight.set(true);
		else
			rangeLight.set(false);
	}
}

