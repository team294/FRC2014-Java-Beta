package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Shoot extends Command {

	
	
    public Shoot() {
    	requires(Robot.winch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	if(Robot.winch.getReady())
    	Robot.winch.disengage();
    	System.out.println("shooting");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	 return (timeSinceInitialized()>0.5);
    	 
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(timeSinceInitialized()>0.5){
    		Robot.winch.engage();
    		Robot.winch.setReady(false);
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.winch.setReady(false);
    }
}
