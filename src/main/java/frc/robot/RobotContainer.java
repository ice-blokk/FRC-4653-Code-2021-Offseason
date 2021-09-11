package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.BasicShoot;
import frc.robot.commands.LimelightAngle;
import frc.robot.commands.LimelightDrive;
import frc.robot.commands.LimelightShoot;
import frc.robot.commands.ResetStuff;
import frc.robot.commands.SetDartToAngle;
import frc.robot.commands.autocommands.AutoSetWithinFramePerimeter;
import frc.robot.commands.autopaths.TestPath;
import frc.robot.commands.defaultcommands.DefaultAngler;
import frc.robot.commands.defaultcommands.DefaultCannon;
import frc.robot.commands.defaultcommands.DefaultDrive;
import frc.robot.commands.defaultcommands.DefaultIntake;
import frc.robot.subsystems.Anglers;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class RobotContainer {

	/*
	To do:
	- Check orientation of drivetrain encoders, invert?
	X Tune shooter velocity PID
	X Make combined intake/index command using TOF/beam break sensors
	X Make function that raises the cannon to a certain height (not just setting dart power)
	- Tune Limelight auto-aim
	- Do auto
	- LED subsystem/disco party button
	*/

	private final Drivetrain drivetrain;
	private final Intake intake;
	private final Cannon cannon;
	private final Anglers anglers; //no they are not fishermen;

	private final Joystick leftStick, rightStick, stick;
	private final XboxController xbox, driver;
	private SendableChooser<Command> chooser;

	public RobotContainer() {
		leftStick = new Joystick(1);
		rightStick = new Joystick(0);

		stick = new Joystick(0);

		driver = new XboxController(0);

		xbox = new XboxController(1);

		drivetrain = new Drivetrain();
		intake = new Intake();
		cannon = new Cannon();
		anglers = new Anglers();

		chooser = new SendableChooser<>();
		
		setDefaultCommands();
		configureButtonBindings();
		initializeAutoChooser();
	}

	private void setDefaultCommands() {
		drivetrain.setDefaultCommand(new DefaultDrive(() -> driver.getY(Hand.kLeft), 
									() -> stick.getTwist(),
									() -> stick.getTriggerReleased(), 
									drivetrain));   
		intake.setDefaultCommand(new DefaultIntake(() -> xbox.getXButton(), () -> xbox.getBButton(),
												   () -> xbox.getYButton(), () -> xbox.getAButton(), intake));
		cannon.setDefaultCommand(new DefaultCannon(() -> xbox.getBackButton(), () -> xbox.getStartButton(), cannon));
		anglers.setDefaultCommand(new DefaultAngler(() -> stick.getRawButton(7), () -> stick.getRawButton(8), anglers));
	}

	private void configureButtonBindings() { 

		new JoystickButton(xbox, 6).whenHeld(new BasicShoot(cannon));

		new JoystickButton(stick, 2).whenHeld(
			new ParallelCommandGroup(
				new LimelightAngle(drivetrain.getLimelight(), anglers),
				new LimelightDrive(drivetrain))
		);

		new JoystickButton(stick, 3).whenHeld(new ResetStuff(drivetrain));

		new JoystickButton(xbox, 5).whenHeld(new LimelightShoot(cannon, drivetrain));

		new JoystickButton(stick, 11).whenHeld(new SetDartToAngle(anglers));

		new JoystickButton(stick, 12).whenPressed(
			new ParallelCommandGroup(
				new AutoSetWithinFramePerimeter(anglers)
			)
		); 

	}

	public void initializeAutoChooser() {
		
		chooser.setDefaultOption("Nothing", null);
		
		chooser.addOption(
			"TestPath", 
			getRamseteCommand(
				TestPath.getTraj(drivetrain.getConfig())
			)
		);
		
	}

	// Ramsete command for paths
	public RamseteCommand getRamseteCommand(Trajectory trajectory) {
		RamseteCommand ramseteCommand = new RamseteCommand(
			trajectory,
			drivetrain::getPose,
			drivetrain.getRamseteController(),
			drivetrain.getFeedforward(),
			drivetrain.getDriveKinematics(),
			drivetrain::getWheelSpeeds,
			drivetrain.getLeftPID(),
			drivetrain.getRightPID(),
			drivetrain::tankDriveVolts,
			drivetrain
			);

		return ramseteCommand;

	}

	public Command getAutomonousCommand() {

		return chooser.getSelected();
	
	} // end of auto command method

} // end of class