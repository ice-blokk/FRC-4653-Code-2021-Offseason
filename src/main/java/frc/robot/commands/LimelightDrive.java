package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Limelight;

/**
 * Command to turn the drivetrain using the limelight
 */

public class LimelightDrive extends CommandBase {

    private final Drivetrain drivetrain;

    private final Limelight limelight;

    boolean finished;
    PIDController pid;

    public LimelightDrive(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        limelight = drivetrain.getLimelight();
        finished = false;
        pid = new PIDController(.04, 0, 0);
    }

    @Override
    public void initialize() {
        finished = false;
    }

    @Override
    public void execute() {
            
            double pidTurn = -pid.calculate(limelight.getX());

            if(Math.abs(pidTurn) < .175) {
                if(pidTurn < 0) {
                    pidTurn -= .20;
                }
                else {
                    pidTurn += .20;
                }
            }

            if(limelight.getValidTarget()) {
                drivetrain.arcadeDrive(0, pidTurn);
            }
            SmartDashboard.putNumber("Limelight pid turn val", pidTurn);
        }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}