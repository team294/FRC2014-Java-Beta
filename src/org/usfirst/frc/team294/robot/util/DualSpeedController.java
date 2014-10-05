package org.usfirst.frc.team294.robot.util;

import edu.wpi.first.wpilibj.SpeedController;

public class DualSpeedController implements SpeedController {

	private SpeedController[] controllers;
	private byte mySyncGroup;
	private int[] inverted;
	
	public DualSpeedController(SpeedController c1, SpeedController c2, byte syncGroup) {
		controllers = new SpeedController[2];
		controllers[0] = c1;
		controllers[1] = c2;
		mySyncGroup = syncGroup;
		inverted = new int[2];
		inverted[0] = inverted[1] = 1;
	}
	
	public void SetInverted(int motor, boolean isInverted) {
		inverted[motor] = isInverted ? -1 : 1;
	}

	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public double get() {
		return controllers[0].get() * inverted[0];
	}

	@Override
	public void set(double speed, byte syncGroup) {
		for (int i=0; i<2; ++i)
			controllers[i].set(speed * inverted[i], mySyncGroup);
	}

	@Override
	public void set(double speed) {
		for (int i=0; i<2; ++i)
			controllers[i].set(speed * inverted[i], mySyncGroup);
	}

	@Override
	public void disable() {
		for (int i=0; i<2; ++i)
			controllers[i].disable();
	}
}
