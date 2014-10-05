package org.usfirst.frc.team294.robot.triggers;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class LimitSwitchTrigger extends Trigger {
    
	private final DigitalInput m_in;
	
	public LimitSwitchTrigger(DigitalInput in) {
		m_in = in;
	}

    public boolean get() {
        return m_in.get() == false;
    }
}
