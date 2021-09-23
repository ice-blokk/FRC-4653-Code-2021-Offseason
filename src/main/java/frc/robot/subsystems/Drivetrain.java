package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Limelight;

public class Drivetrain extends SubsystemBase {
	
	private final CANSparkMax frontLeft, frontRight, backLeft, backRight;
	private final SpeedControllerGroup left, right;
	private final DifferentialDrive drive;

	private final AHRS ahrs;
	private final Limelight limelight;

	// Odometry + Trajectory Stuff

	private final DifferentialDriveOdometry odometry;

	private CANEncoder leftEncoder;
	private CANEncoder rightEncoder;

	DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(Constants.kTrackwidthMeters);

	SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Constants.ksVolts, Constants.kvVoltSecondsPerMeter, Constants.kaVoltSecondsSquaredPerMeter);
	DifferentialDriveVoltageConstraint autoVoltageConstraint = new DifferentialDriveVoltageConstraint(feedforward, kDriveKinematics, 10);
	TrajectoryConfig config = new TrajectoryConfig(
		Constants.kMaxSpeedMetersPerSecond, 
		Constants.kMaxAccelerationMetersPerSecondSquared)
		.setKinematics(kDriveKinematics)
		.addConstraint(autoVoltageConstraint);
	
	TrajectoryConfig configSlow = new TrajectoryConfig(
		1, 
		1)
		.setKinematics(kDriveKinematics)
		.addConstraint(autoVoltageConstraint);

	TrajectoryConfig configFast = new TrajectoryConfig(
		3, 
		3)
		.setKinematics(kDriveKinematics)
		.addConstraint(autoVoltageConstraint);

	TrajectoryConfig configReverse = new TrajectoryConfig(
		Constants.kMaxSpeedMetersPerSecond, 
		Constants.kMaxAccelerationMetersPerSecondSquared)
		.setKinematics(kDriveKinematics)
		.addConstraint(autoVoltageConstraint)
		.setReversed(true);

	TrajectoryConfig configButFaster = new TrajectoryConfig(
		4, 
		4)
		.setKinematics(kDriveKinematics)
		.addConstraint(autoVoltageConstraint);
		
	
	public Drivetrain() {
		frontLeft = new CANSparkMax(Constants.FRONT_LEFT_ADDRESS, MotorType.kBrushless);
		frontRight = new CANSparkMax(Constants.FRONT_RIGHT_ADDRESS, MotorType.kBrushless);
		backLeft = new CANSparkMax(Constants.BACK_LEFT_ADDRESS, MotorType.kBrushless);
		backRight = new CANSparkMax(Constants.BACK_RIGHT_ADDRESS, MotorType.kBrushless);
		
		frontLeft.restoreFactoryDefaults();
		frontRight.restoreFactoryDefaults();
		backLeft.restoreFactoryDefaults();
		backRight.restoreFactoryDefaults();

		frontLeft.setIdleMode(IdleMode.kBrake);
		frontRight.setIdleMode(IdleMode.kBrake);
		backLeft.setIdleMode(IdleMode.kBrake);
		backRight.setIdleMode(IdleMode.kBrake);

		left = new SpeedControllerGroup(frontLeft, backLeft);
		right = new SpeedControllerGroup(frontRight, backRight);

		drive = new DifferentialDrive(left, right);

		ahrs = new AHRS(SPI.Port.kMXP);

		limelight = new Limelight();

		resetEncoders();

		odometry = new DifferentialDriveOdometry(getHeading());

		leftEncoder = frontLeft.getEncoder();
		rightEncoder = frontRight.getEncoder();
	}

	public void tankDrive(double leftSpeed, double rightSpeed, boolean sqareInputs) {
		drive.tankDrive(leftSpeed, rightSpeed, sqareInputs);
	}

	public void tankDrive(double leftSpeed, double rightSpeed) {
		drive.tankDrive(leftSpeed, rightSpeed);
	}

	public void arcadeDrive(double speed, double rotate, boolean squareInputs) {
		drive.arcadeDrive(speed, rotate, squareInputs);
	}
	public void arcadeDrive(double speed, double rotate) {
		drive.arcadeDrive(speed, rotate);
	}

	public double getEncoderLeft() {
		return frontLeft.getEncoder().getPosition() + backLeft.getEncoder().getPosition();
	}
	public double getEncoderRight() {
		return frontRight.getEncoder().getPosition() + backRight.getEncoder().getPosition();
	}
	public double getEncoderAverage() {
		return (getEncoderLeft() + -getEncoderRight()) / 2;
	}
	public void resetEncoders() {
		frontLeft.getEncoder().setPosition(0);
		frontRight.getEncoder().setPosition(0);
		backLeft.getEncoder().setPosition(0);
		backRight.getEncoder().setPosition(0);
	}

	double gyroOffset = 0;
	public double getAngle() {
		return ahrs.getAngle() - gyroOffset;
	}
	public void resetAngle() {
		gyroOffset = ahrs.getAngle();
	}

	public Limelight getLimelight() {
		return limelight;
	}

	// Odometry Methods
	public Pose2d getPose() {
		return odometry.getPoseMeters();
	}

	public DifferentialDriveWheelSpeeds getWheelSpeeds() {
		return new DifferentialDriveWheelSpeeds(
			// divide by gear ratio, multiply by wheel circumference (converted to meters), 
			// convert to per second by dividing by 60 (since getVelocity gives RPM)
			leftEncoder.getVelocity() / 7.31 * Math.PI * Units.inchesToMeters(6.0) / 60, 
			rightEncoder.getVelocity() / 7.31 * Math.PI * Units.inchesToMeters(6.0) / 60
			);
	}

	public void tankDriveVolts(double leftVolts, double rightVolts) {
		left.setVoltage(leftVolts);
		right.setVoltage(-rightVolts);
		drive.feed();
		SmartDashboard.putNumber("Left Volts", leftVolts);
		SmartDashboard.putNumber("Right Volts", rightVolts);
	}

	public void resetOdometry(Pose2d pose) {
		resetEncoders();
		odometry.resetPosition(pose, ahrs.getRotation2d());
	}

	public void setMaxOutput(double maxOutput) {
		drive.setMaxOutput(maxOutput);
	}

	public void zeroHeading() {
		ahrs.reset();
	}

	public Rotation2d getHeading() {
		return Rotation2d.fromDegrees(-ahrs.getAngle());
	}

	public double getTurnRate() {
		return -ahrs.getRate();
	}
	

	// Getting distance from encoder values
	// getPosition() returns the amount of turns of the shaft, so to get distance (in inches)
	// divide shaft turns by gear ratio and multiply by the circumferance (pi times diameter of the wheels)
	// then convert from inches to meters
	public double getLeftEncoderDistance() {
		return Units.inchesToMeters((leftEncoder.getPosition() / 7.31) * Math.PI * 6.0);
	}

	public double getRightEncoderDistance() {
		return Units.inchesToMeters((-rightEncoder.getPosition() / 7.31) * Math.PI * 6.0);
	}

	// Methods for creating trajectories
	public SimpleMotorFeedforward getFeedforward() {
		return feedforward;
	}
 
	public DifferentialDriveVoltageConstraint getAutoVoltageConstrain() {
		return autoVoltageConstraint;
	}

	public TrajectoryConfig getConfig() {
		return config;
	}

	public TrajectoryConfig getSlowConfig() {
		return configSlow;
	}

	public TrajectoryConfig getFastConfig() {
		return configFast;
	}

	public TrajectoryConfig getReverseConfig() {
		return configReverse;
	}

	public TrajectoryConfig getFasterConfig() {
		return configButFaster;
	}

	public RamseteController getRamseteController() {
		return new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta);
	}

	public PIDController getLeftPID() {
		return new PIDController(Constants.kPDriveVel, 0, 0);
	}

	public PIDController getRightPID() {
		return new PIDController(Constants.kPDriveVel, 0, 0);
	}

	public DifferentialDriveKinematics getDriveKinematics() {
		return kDriveKinematics;
	}

	public Pose2d getCurrentPose() {
		return odometry.getPoseMeters();
	}
	
	@Override
	public void periodic() {
	//	System.out.println("L: " + getEncoderLeft() + " R: " + getEncoderRight());
		SmartDashboard.putBoolean("Limelight Valid Target", limelight.getValidTarget());
		SmartDashboard.putNumber("Limelight Area", limelight.getArea());
		SmartDashboard.putNumber("Limelight X", limelight.getX());
		SmartDashboard.putNumber("Limelight Y", limelight.getY());
		SmartDashboard.putNumber("Limelight Vertical", limelight.getVertical());
		SmartDashboard.putNumber("Limelight Horizontal", limelight.getHorizontal());

		odometry.update(getHeading(), getLeftEncoderDistance() , getRightEncoderDistance());

		SmartDashboard.putNumber("Odometry X (meters)", odometry.getPoseMeters().getX());
		SmartDashboard.putNumber("Odometry Y (meters)", odometry.getPoseMeters().getY());
		SmartDashboard.putNumber("Odomemtry Rotation (degrees)", odometry.getPoseMeters().getRotation().getDegrees());
		
		SmartDashboard.putNumber("Encoder Distance Left (m)", getLeftEncoderDistance());
		SmartDashboard.putNumber("Encoder Distance Right (m)", getRightEncoderDistance());

		//SmartDashboard.putNumber("Left Measurement", getWheelSpeeds().leftMetersPerSecond);
		//SmartDashboard.putNumber("Left Reference", getLeftPID().getSetpoint());

		SmartDashboard.putNumber("Encoder average", getEncoderAverage());

		SmartDashboard.putNumber("Gyro Degrees", getAngle());
	}
}	