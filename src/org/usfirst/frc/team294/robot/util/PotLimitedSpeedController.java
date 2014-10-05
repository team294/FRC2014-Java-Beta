package org.usfirst.frc.team294.robot.util;

import java.lang.String;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedController;

public class PotLimitedSpeedController implements SpeedController {

	private SpeedController m_output;
	private AnalogInput m_pot;
	private String m_lowLimitPref;
	private String m_highLimitPref;
	private double m_forcedLowLimit = Double.NEGATIVE_INFINITY;
	private double m_forcedHighLimit = Double.POSITIVE_INFINITY;
	private boolean m_inverted = false;
	private boolean m_failsafe = true;
	private double m_scale = 1.0;
    private final Object lock = new Object();
	
	public PotLimitedSpeedController(SpeedController output, AnalogInput pot, String lowLimitPref, String highLimitPref) {
		m_output = output;
		m_pot = pot;
		m_lowLimitPref = lowLimitPref;
		m_highLimitPref = highLimitPref;
	}
	
	public void setInverted(boolean inverted) {
		synchronized (lock) {
			m_inverted = inverted;
		}
	}

	public void setFailsafe(boolean failsafe) {
		synchronized (lock) {
			m_failsafe = failsafe;
		}
	}

	public void setScale(double scale) {
		synchronized (lock) {
			m_scale = scale;
		}
	}

	public void forceLowLimit(double limit) {
		synchronized (lock) {
			m_forcedLowLimit = limit;
		}
	}
	
	public void forceHighLimit(double limit) {
		synchronized (lock) {
			m_forcedHighLimit = limit;
		}
	}

	@Override
	public void pidWrite(double output) {
    	set(output);
	}

	@Override
	public double get() {
		return m_output.get();
	}

	@Override
	public void set(double speed, byte syncGroup) {
		Preferences prefs = Preferences.getInstance();
		double potValue = m_pot.getValue()*m_scale;
		double lowLimit, highLimit;
		synchronized (lock) {
			if (m_forcedLowLimit != Double.NEGATIVE_INFINITY)
				lowLimit = m_forcedLowLimit;
			else
				lowLimit = prefs.getDouble(m_lowLimitPref, Double.NEGATIVE_INFINITY);
			if (m_forcedHighLimit != Double.POSITIVE_INFINITY)
				highLimit = m_forcedHighLimit;
			else
				highLimit = prefs.getDouble(m_highLimitPref, Double.POSITIVE_INFINITY);
		}
		//if (lowLimit > highLimit)
		//    lowLimit, highLimit = highLimit, lowLimit;
		// if pot disconnected, don't let it drive at all
		if (m_failsafe && potValue < 0) {
			m_output.set(0, syncGroup);
			return;
		}
		if (!m_inverted) {
			if (speed < 0 && potValue < lowLimit)
				m_output.set(0, syncGroup);
			else if (speed > 0 && potValue > highLimit)
				m_output.set(0, syncGroup);
			else
				m_output.set(speed, syncGroup);
		} else {
			if (speed > 0 && potValue < lowLimit)
				m_output.set(0, syncGroup);
			else if (speed < 0 && potValue > highLimit)
				m_output.set(0, syncGroup);
			else
				m_output.set(speed, syncGroup);
		}
	}

	@Override
	public void set(double speed) {
		set(speed, (byte) 0);
	}

	@Override
	public void disable() {
		m_output.disable();
	}
}
