package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.util.LinearVictor884;
import org.usfirst.frc.team294.robot.util.PotLimitedSpeedController;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Pivot extends PIDSubsystem {

	SpeedController pivotMotorUnlimited = new LinearVictor884(RobotMap.kPWM_pivotMotor);
	AnalogInput pivotPot = new AnalogInput(RobotMap.kAIN_pivotPot);
	
	PotLimitedSpeedController pivotMotor = new PotLimitedSpeedController(pivotMotorUnlimited, pivotPot, "pivMinLimit", "pivMaxLimit");

	public enum Setpoint {
		kStart,
		kHoldAuto,
		kHuman,
		kPass,
		kAutoShot,
		kFarShot,
		kCloseShot,
		kIntake,
		kLowGoal,
	}
	Setpoint m_setpoint;

	// Initialize your subsystem here
	public Pivot() {
		// Use these to get going:
		// setSetpoint() -  Sets where the PID controller should move the system
		//                  to
		// enable() - Enables the PID controller.
		super(Preferences.getInstance().getDouble("pivP", 0.0),
				Preferences.getInstance().getDouble("pivI", 0.0),
				Preferences.getInstance().getDouble("pivD", 0.0));
		setInputRange(Preferences.getInstance().getDouble("pivMinLimit", 0.0),
				Preferences.getInstance().getDouble("pivMaxLimit", 5.0));
		//setOutputRange(-0.75, 0.75);
		//setTolerance(0.1);
		setAbsoluteTolerance(0.03);
		pivotMotor.setInverted(true);
		pivotMotor.setScale(1.0/200.0);
	}
    
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	protected double returnPIDInput() {
		return pivotPot.getAverageValue() / 200.0;
	}
	
	protected void usePIDOutput(double output) {
		pivotMotor.set(-output);
	}

	public void stop() {
		disable();
	}
	
	public void setManual(double value) {
		if (getPIDController().isEnable())
			disable();
		pivotMotor.set(value);
	}
	
	public boolean isIntakeUpOk() {
		double pivStartSetpoint = Preferences.getInstance().getDouble("pivStartSetpoint", Double.POSITIVE_INFINITY);
		if (pivStartSetpoint == Double.POSITIVE_INFINITY)
			return false;
		return getPosition() < (pivStartSetpoint+2);
	}
	
	public void goHome() {
		setPrefSetpoint(Setpoint.kStart);
	}

	private void setPrefSetpoint(String pref) {
		if (pref == null)
			return;
		double setp = Preferences.getInstance().getDouble(pref, Double.POSITIVE_INFINITY);
		if (setp == Double.POSITIVE_INFINITY)
			return;
		setSetpoint(setp);
		if (!getPIDController().isEnable()) {
			getPIDController().reset();
			enable();
		}
	}

	private String getSetpointPrefName(Setpoint setpoint) {
		switch (setpoint) {
		case kStart:		return "pivStartSetpoint";
		case kHoldAuto:		return "pivHoldAutoSetpoint";
		case kHuman:		return "pivHumanLoadSetpoint";
		case kPass:			return "pivPassSetpoint";
		case kAutoShot:		return "pivAutoShotSetpoint";
		case kFarShot:		return "pivFarShotSetpoint";
		case kCloseShot:	return "pivCloseShotSetpoint";
		case kIntake:		return "pivIntakeSetpoint";
		case kLowGoal:		return "pivLowGoalSetpoint";
		default:			return null;
		}
	}

	public void setPrefSetpoint(Setpoint setpoint) {
		if (setpoint == Setpoint.kFarShot) {
			// special handling for far shot
			if (isIntake())
				setPrefSetpoint("pivFarShotSetpointFromIntake");
			else
				setPrefSetpoint("pivFarShotSetpoint");
		}
		else {
			// all others
			setPrefSetpoint(getSetpointPrefName(setpoint));
		}
		synchronized (this) {
			m_setpoint = setpoint;
			// override auto shot to far shot
			if (setpoint == Setpoint.kAutoShot)
				setpoint = Setpoint.kFarShot;
		}
	}
	
	public synchronized Setpoint getPrefSetpoint() {
		return m_setpoint;
	}
	
	public synchronized boolean isFarShot() {
		return m_setpoint == Setpoint.kFarShot;
	}
	
	public synchronized boolean isCloseShot() {
		return m_setpoint == Setpoint.kCloseShot;
	}
	
	public synchronized boolean isIntake() {
		return m_setpoint == Setpoint.kIntake;
	}
	
	public boolean onTarget() {
		//logging.debug("cur: %.2f setpoint: %.2f error: %.2f ontarget: %s",
		//		self.pidSource.PIDGet(),
		//		self.pid.GetSetpoint(),
		//		self.pid.GetError(),
		//		self.pid.OnTarget())
		return super.onTarget();
	}
	
	public void tweakSetpoint(double amt) {
		if (getPIDController().isEnable()) {
			double oldSetpoint = getSetpoint();
			double newSetpoint = oldSetpoint + amt;
			// Update preferences so the robot remembers it for next time
			String pref;
			synchronized (this) {
				// don't update start setpoint
				if (m_setpoint == Setpoint.kStart)
					return;
				pref = getSetpointPrefName(m_setpoint);
			}
			if (pref == null)
				return;
			Preferences.getInstance().putDouble(pref, newSetpoint);
			Preferences.getInstance().save();
			setSetpoint(newSetpoint);
		} else {
			setSetpoint(getPosition() + amt);
			getPIDController().reset();
			enable();
		}
	}

	public void tweakDown() {
		tweakSetpoint(4);
	}
	
	public void tweakUp() {
		tweakSetpoint(-4);
	}
}
