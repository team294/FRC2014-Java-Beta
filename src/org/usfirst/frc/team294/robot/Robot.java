
package org.usfirst.frc.team294.robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team294.robot.commands.*;
import org.usfirst.frc.team294.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static PowerDistributionPanel pdp;
	public static Gyro gyro;

	public static Drivetrain drivetrain;
	public static Intake intake;
	public static Jaw jaw;
	public static Kicker kicker;
	public static Pivot pivot;
	public static RangeFinder rangeFinder;
	public static Shifter shifter;
	public static Winch winch;

	public static OI oi;

    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	pdp = new PowerDistributionPanel();
    	gyro = new Gyro(RobotMap.kAIN_gyro);

    	drivetrain = new Drivetrain();
		intake = new Intake();
		jaw = new Jaw();
		kicker = new Kicker();
		pivot = new Pivot();
		rangeFinder = new RangeFinder();
		shifter = new Shifter();
		winch = new Winch();
		
		SmartDashboard.putData(drivetrain);
		SmartDashboard.putData(intake);
		SmartDashboard.putData(jaw);
		SmartDashboard.putData(kicker);
		SmartDashboard.putData(pivot);
		SmartDashboard.putData(rangeFinder);
		SmartDashboard.putData(shifter);
		SmartDashboard.putData(winch);
		
		SmartDashboard.putData(new IntakeRun());
		SmartDashboard.putData(new IntakeReverse());
		SmartDashboard.putData(new IntakeStop());
		SmartDashboard.putData(new JawClose());
		SmartDashboard.putData(new JawOpen());
		SmartDashboard.putData(new ShiftDown());
		SmartDashboard.putData(new ShiftUp());

		oi = new OI();
        // instantiate the command used for the autonomous period
        //autonomousCommand = new ExampleCommand();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        //autonomousCommand.start();
    }

    /**
     * This function is called periodically during disabled
     */
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        //autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
