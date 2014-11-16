package org.usfirst.frc.team294.robot.commands;



import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class FiringSequence extends CommandGroup {

	public  FiringSequence() {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		//      addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		//      addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.


		addSequential(new JawOpen());
		addSequential(new PrintCommand("toWait"));
		addSequential(new WaitCommand(0.15));
		addSequential(new Shoot());
		addSequential(new PrintCommand("toWait"));
		addSequential(new WaitCommand(1));
		addParallel(new JawClose());
		addSequential(new Engage());

	}
	protected void interrupted() {

	}
}
