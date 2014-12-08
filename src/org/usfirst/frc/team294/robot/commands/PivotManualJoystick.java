package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PivotManualJoystick extends Command {
	private GenericHID m_stick;
	
	public PivotManualJoystick(GenericHID stick) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.pivot);
		m_stick = stick;
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.pivot.stop();
		System.out.println("execute");
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.println("execute");
    	Robot.pivot.setManual(m_stick.getY());
    	double value = Robot.pivot.getPosition();
    	SmartDashboard.putNumber("DB.Slider 0", value);
    	System.out.println("Pot value:" + value);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		Robot.pivot.stop();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
