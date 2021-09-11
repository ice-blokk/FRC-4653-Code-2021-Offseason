package frc.robot;

public final class Constants {
    //CAN IDs
    public static final int FRONT_LEFT_ADDRESS = 1,
                            FRONT_RIGHT_ADDRESS = 2,
                            BACK_LEFT_ADDRESS = 3,
                            BACK_RIGHT_ADDRESS = 4,
                            INTAKE_SUCKER_ADDRESS = 8,
                            INTAKE_ARM_ADDRESS = 9,
                            TOP_SHOOTER_ADDRESS = 6,
                            BOTTOM_SHOOTER_ADDRESS = 7,
                            FEEDER_ADDRESS = 10,
                            CLIMBER_ADDRESS = 11,
                            DART_ADDRESS = 5,
                            LEAD_ADDRESS = 13;

    //DIO ports
    public static final int DART_TOP_LIMIT_ADDRESS = 0,
                            DART_BOTTOM_LIMIT_ADDRESS = 1,
                            LEAD_FRONT_LIMIT_ADDRESS = 4,
                            LEAD_BACK_LIMIT_ADDRESS = 2,
                            INTAKE_BALL_SENSOR_ADDRESS = 3  ,
                            SHOOTER_BALL_SENSOR_ADDRESS = 5;

    //Analog ports
    public static final int DART_POTENTIOMETER_ADDRESS = 0;

    //PWM ports
    public static final int LED_STRIP_ADDRESS = 0;

    //Other constants
    public static final double SHOOTER_VELOCITY_KP = 0.0005,
                               SHOOTER_VELOCITY_KF = 0.0002148,
                               SHOOTER_VELOCITY_KI = 0.00001,
                               SHOOTER_MAX_VELOCITY = 4200;
                               //SHOOTER_MAX_VELOCITY = 5600;

    public static final int LED_STRIP_LENGTH = 62;

    // Odometry + Trajectory Constants
    
    // Characterization constants gotten from team 5910 (they seem to work
    // better than the tool gotten from our testing)
    public static final double ksVolts = 0.268;
    public static final double kvVoltSecondsPerMeter = 1.89;
    public static final double kaVoltSecondsSquaredPerMeter = .0243; // this is the value from team 5190


    // P val gotten from characterization
    public static final double kPDriveVel = 0.205;

    public static final double kTrackwidthMeters = 0.5588;

	public static final double kMaxSpeedMetersPerSecond = 1.5;
	public static final double kMaxAccelerationMetersPerSecondSquared = 1.7;
	
	public static final double kRamseteB = 2;
	public static final double kRamseteZeta = 0.7;

}
