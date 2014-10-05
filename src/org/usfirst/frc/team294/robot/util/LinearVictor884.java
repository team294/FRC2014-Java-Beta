package org.usfirst.frc.team294.robot.util;

import edu.wpi.first.wpilibj.Victor;

public class LinearVictor884 extends Victor {
	public LinearVictor884(final int channel) {
		super(channel);
	}

	private double Linearize(double x) {
		if (x > 1)
			x = 1;
		if (x < -1)
			x = -1;
		if (x >= 0)
			return 3.1199*x*x*x*x - 4.4664*x*x*x + 2.2378*x*x + 0.1222*x;
		x = -x;
		return -(3.1199*x*x*x*x - 4.4664*x*x*x + 2.2378*x*x + 0.1222*x);
	}

	@SuppressWarnings("deprecation")
	public void set(double speed, byte syncGroup) {
		super.set(Linearize(speed), syncGroup);
	}
	
	public void set(double speed) {
		super.set(Linearize(speed));
	}
}
