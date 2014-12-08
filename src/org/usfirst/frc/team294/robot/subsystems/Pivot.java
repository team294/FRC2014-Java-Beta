package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.util.PotLimitedSpeedController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Preferences;
//import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Pivot extends Subsystem {

	/**
	 * pivot Motor Unlimited
	 */
	CANTalon pivotMotor = new CANTalon(RobotMap.kPWM_pivotMotor);
	
	double setp;
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
		pivotMotor.setPID(Preferences.getInstance().getDouble("pivP", 0.0),
				Preferences.getInstance().getDouble("pivI", 0.0),
				Preferences.getInstance().getDouble("pivD", 0.0));
		pivotMotor.setFeedbackDevice(FeedbackDevice.AnalogPot);
		
		System.out.println("limit="+Preferences.getInstance().getInt("pivMinLimit", 0));		
		System.out.println("limit="+Preferences.getInstance().getInt("pivMaxLimit", 0));

		//pivotMotor.setForwardSoftLimit(Preferences.getInstance().getInt("pivMinLimit", 0));
		//pivotMotor.setReverseSoftLimit(Preferences.getInstance().getDouble("pivMaxLimit", 0));
		//pivotMotor.enableForwardSoftLimit(true);
		//pivotMotor.enableReverseSoftLimit(true);
		
		//setOutputRange(-0.75, 0.75);
		//setTolerance(0.1);
		
		pivotMotor.reverseSensor(true);
		//pivotMotor.setScale(1.0/200.0);
	}
    
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
	
	public boolean onTarget(){
		return Math.abs(pivotMotor.getClosedLoopError())<30;
	}
	
	public void stop() {
		pivotMotor.disableControl();
	}
	
	public void setManual(double value) {
		if (pivotMotor.getControlMode() != ControlMode.PercentVbus) {
			pivotMotor.disableControl();
			pivotMotor.changeControlMode(ControlMode.PercentVbus);
		}
		pivotMotor.set(value);
		pivotMotor.enableControl();
	}
	public int getPosition(){
		return pivotMotor.getAnalogInPosition();
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
		setp = Preferences.getInstance().getDouble(pref, Double.POSITIVE_INFINITY);
		if (setp == Double.POSITIVE_INFINITY)
			return;
		if (pivotMotor.getControlMode() != ControlMode.Position) {
			pivotMotor.disableControl();
			pivotMotor.changeControlMode(ControlMode.Position);
		}
		pivotMotor.set(setp);
		pivotMotor.enableControl();
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
	
	public void tweakSetpoint(double amt) {
		if (pivotMotor.getControlMode()==ControlMode.Position) {
			double oldSetpoint = setp;
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
			pivotMotor.set(newSetpoint);
		} else {
			pivotMotor.disableControl();
			pivotMotor.changeControlMode(ControlMode.Position);
			pivotMotor.set(pivotMotor.getPosition() + amt);
			//getPIDController().reset();
			pivotMotor.enableControl();
		}
	}

	public void tweakDown() {
		tweakSetpoint(4);
	}
	
	public void tweakUp() {
		tweakSetpoint(-4);
	}
}
