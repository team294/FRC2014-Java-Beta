package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Engage extends Command {

    public Engage() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.winch);
    	setInterruptible(false);
    	System.out.println("engage");
   
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.winch.back();
    	System.out.println("execute engage");
   
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.winch.isBack();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.winch.stop();
    	Robot.winch.setReady(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.winch.stop();
    	Robot.winch.setReady(false);
    }
}