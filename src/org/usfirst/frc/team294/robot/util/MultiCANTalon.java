package org.usfirst.frc.team294.robot.util;


import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.SpeedController;

public class MultiCANTalon implements SpeedController {

	private CANTalon[] controllers;
	
	public MultiCANTalon(int[] motors) {
		controllers = new CANTalon[motors.length];
		for (int i=0; i < motors.length; i++)
			controllers[i] = new CANTalon(motors[i]);
		for (int i=1; i < motors.length; i++) {
			controllers[i].changeControlMode(TalonControlMode.Follower);
		}
	}
	
	public void SetInverted(int motor, boolean isInverted) {
		assert(motor != 0);
		controllers[motor].reverseOutput(isInverted);
	}

	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public double get() {
		return controllers[0].get();
	}

	@Override
	public void set(double speed) {
		controllers[0].set(speed);
		for (int i=1; i < controllers.length; i++) {
			controllers[i].set(controllers[0].getDeviceID());
		}
	}

	@Override
	public void disable() {
		controllers[0].disable();
	}

	@Override
	public void setInverted(boolean isInverted) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getInverted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stopMotor() {
		// TODO Auto-generated method stub
		
	}
}
