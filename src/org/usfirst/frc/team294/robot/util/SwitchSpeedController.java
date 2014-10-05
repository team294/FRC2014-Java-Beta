package org.usfirst.frc.team294.robot.util;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedController;

public class SwitchSpeedController implements SpeedController {

	private SpeedController m_controller;
	private DigitalInput m_limitSwitch;
	
	public SwitchSpeedController(SpeedController controller, DigitalInput limitSwitch) {
		m_controller = controller;
		m_limitSwitch = limitSwitch;
	}

	@Override
	public void pidWrite(double output) {
		set(output);
	}

	@Override
	public double get() {
		return m_controller.get();
	}

	@Override
	public void set(double speed, byte syncGroup) {
		if (!m_limitSwitch.get())
			m_controller.set(0.0, syncGroup);
		else
			m_controller.set(speed, syncGroup);
	}

	@Override
	public void set(double speed) {
		set(speed, (byte) 0);
	}

	@Override
	public void disable() {
		m_controller.disable();
	}
}
