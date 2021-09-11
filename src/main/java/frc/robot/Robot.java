package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.util.Limelight;

public class Robot extends TimedRobot {

	@SuppressWarnings("unused")
	private RobotContainer robotContainer;
	Joystick stick = new Joystick(0);
	Limelight limelight;


	@Override
	public void robotInit() {
		robotContainer = new RobotContainer();
		limelight = new Limelight();
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
		SmartDashboard.putNumber("Slider val", stick.getRawAxis(3));
		SmartDashboard.putNumber("Limelight Shoot Speed", .18 * limelight.getArea() - .88);
	}
	
	@Override
	public void autonomousInit() {
		if(robotContainer.getAutomonousCommand() != null) {
			robotContainer.getAutomonousCommand().schedule();
		}
	}

	@Override
	public void autonomousPeriodic() {
		CommandScheduler.getInstance().run();
		
	}

	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {
	}

}