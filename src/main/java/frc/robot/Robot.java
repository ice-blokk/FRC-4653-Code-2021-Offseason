package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.util.Limelight;
import frc.robot.util.Pixy2;
import frc.robot.util.Pixy2Obj;

public class Robot extends TimedRobot {

	@SuppressWarnings("unused")
	private RobotContainer robotContainer;
	Joystick stick = new Joystick(0);
	Limelight limelight;
	Pixy2Obj pixy;


	@Override
	public void robotInit() {
		robotContainer = new RobotContainer();
		limelight = new Limelight();
		pixy = new Pixy2Obj();
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
		SmartDashboard.putNumber("Slider val", stick.getRawAxis(3));
		SmartDashboard.putNumber("Limelight Shoot Speed", .18 * limelight.getArea() - .88);

		pixy.updateValues();
		SmartDashboard.putNumber("Pixy x", pixy.getPixyX());
		SmartDashboard.putNumber("Pixy y", pixy.getPixyY());
		SmartDashboard.putNumber("Pixy sig", pixy.getSig());
		SmartDashboard.putNumber("Pixy height", pixy.getHeight());
		SmartDashboard.putNumber("Pixy width", pixy.getWidth());
		SmartDashboard.putNumber("Pixy area", pixy.getPixyArea());
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