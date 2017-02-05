package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.commands.IntakeStop;
import org.usfirst.frc.team294.robot.triggers.LimitSwitchTrigger;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

	CANTalon intakeWheelMotor = new CANTalon(RobotMap.kPWM_intakeWheelMotor);
	DigitalInput buttonIntake = new DigitalInput(RobotMap.kDIN_buttonIntake);
	LimitSwitchTrigger buttonIntakeHit = new LimitSwitchTrigger(buttonIntake);

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		buttonIntakeHit.whileActive(new IntakeStop());
	}
	
	public void run() {
		intakeWheelMotor.set(-1);
	}
	
	public void stop() {
		intakeWheelMotor.set(0);
	}
	
	public void reverse() {
		intakeWheelMotor.set(1);
	}
	
	public void torque() {
		intakeWheelMotor.set(-0.055);
	}
	
	public void manual(double speed) {
		intakeWheelMotor.set(speed);
	}
}

