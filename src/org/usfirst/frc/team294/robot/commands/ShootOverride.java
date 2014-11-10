package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShootOverride extends Command {

	
	
    public ShootOverride() {
    	requires(Robot.winch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {


    	Robot.winch.disengage();
    	System.out.println("shooting override");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	 return false;
  
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.winch.engage();
    	//Robot.winch.setReady(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.winch.engage();
    }
}
