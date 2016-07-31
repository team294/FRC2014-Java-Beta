package org.usfirst.frc.team294.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
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
	public static AnalogGyro gyro;

	public static Drivetrain drivetrain;
	public static Intake intake;
	public static Jaw jaw;
	public static Pivot pivot;
	public static RangeFinder rangeFinder;
	public static Shifter shifter;
	public static Winch winch;

	public static OI oi;

	public static boolean disabledTrigPressed = false;
	public static boolean disabledButton2Pressed = false;
	public static boolean disabledButton5Pressed = false;
	public static boolean disabledButton4Pressed = false;
	public static boolean disabledButton3Pressed = false;

	public static int autoMode = 0;

	public static float autoDelay = 0;

	public static int startPosition = 0;

	Command autonomousCommand;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		pdp = new PowerDistributionPanel();
		gyro = new AnalogGyro(RobotMap.kAIN_gyro);

		drivetrain = new Drivetrain();
		intake = new Intake();
		jaw = new Jaw();
		pivot = new Pivot();
		rangeFinder = new RangeFinder();
		shifter = new Shifter();
		winch = new Winch();

		SmartDashboard.putData(drivetrain);
		SmartDashboard.putData(intake);
		SmartDashboard.putData(jaw);
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
		SmartDashboard.putData(new Engage());
		SmartDashboard.putData(new Shoot());

		oi = new OI();
		// instantiate the command used for the autonomous period
		// autonomousCommand = new ExampleCommand();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		// autonomousCommand.start();
		switch (autoMode) {

		case 0:
			break;

		case 1:
			new AutoModeDrive();
			break;

		case 2:
			new AutoModeDriveAndShoot();
			break;

		case 3:
			new AutoModeHotShot();
			break;

		default:
			break;
		}
	}

	/**
	 * This function is called periodically during disabled
	 */
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		if ((!disabledTrigPressed) && oi.coStick.getTrigger() && autoMode < 3)
			autoMode++;
		disabledTrigPressed = oi.coStick.getTrigger();
		if ((!disabledButton2Pressed) && oi.coStick.getRawButton(2)
				&& autoMode > 0)
			autoMode--;
		disabledButton2Pressed = oi.coStick.getRawButton(2);
		if ((!disabledButton4Pressed) && oi.coStick.getRawButton(4))
			autoMode += 0.1;
		disabledButton4Pressed = oi.coStick.getRawButton(4);
		if ((!disabledButton5Pressed) && oi.coStick.getRawButton(5)
				&& autoDelay > 0)
			autoDelay -= 0.1;
		disabledButton5Pressed = oi.coStick.getRawButton(5);

		if ((!disabledButton3Pressed) && oi.coStick.getRawButton(3)) {

			if (startPosition < 2)
				startPosition++;
			else
				startPosition = 0;
		}
		disabledButton3Pressed = oi.coStick.getRawButton(3);
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
		// autonomousCommand.cancel();
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
