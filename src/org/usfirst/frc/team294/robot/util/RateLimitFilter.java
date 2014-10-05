package org.usfirst.frc.team294.robot.util;

import edu.wpi.first.wpilibj.Timer;

/** Implements a rate limiter to limit the rising and falling
 * rates of the input value.
 */
public class RateLimitFilter {
	private double m_riseRate, m_fallRate;
	private double m_thres = 0.0;
	private double m_prev = 0.0;
	private double m_prevTime = Double.NEGATIVE_INFINITY;

	/** @note riseRate and fallRate are in units/sec */
	public RateLimitFilter(double riseRate) {
		m_riseRate = riseRate;
		m_fallRate = -riseRate;
	}

	/** @note fallRate should be negative */
	public RateLimitFilter(double riseRate, double fallRate) {
		m_riseRate = riseRate;
		m_fallRate = fallRate;
	}
	
	public void setThreshold(double threshold) {
		m_thres = threshold;
	}

	/** Updates the filter and returns the filtered value. */
	public double update(double value) {
		double curTime = Timer.getFPGATimestamp();
		double result;
		if (m_prevTime == Double.NEGATIVE_INFINITY || Math.abs(m_prev) < m_thres)
			result = value;
		else {
			double deltaTime = curTime - m_prevTime;
			double rate = (value - m_prev) / deltaTime;
			if (rate > m_riseRate)
				result = m_prev + (deltaTime * m_riseRate);
			else if (rate < m_fallRate)
				result = m_prev + (deltaTime * m_fallRate);
			else
				result = value;
		}
		if (result > 1)
			result = 1;
		if (result < -1)
			result = -1;
		m_prev = result;
		m_prevTime = curTime;
		return result;
	}
	
	public double getLastValue() {
		return m_prev;
	}
}
