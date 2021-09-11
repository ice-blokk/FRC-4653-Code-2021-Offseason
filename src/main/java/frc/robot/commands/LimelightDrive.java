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
        pid = new PIDController(.4, .01, .01);
    }

    @Override
    public void initialize() {
        finished = false;
    }

    @Override
    public void execute() {
        /* if(limelight.getValidTarget()) {
            double turn = -pid.calculate(limelight.getX(), 0) * .1;
            if(Math.abs(turn) > .8) {
                turn = Math.copySign(turn, .75);
            }
            else if(Math.abs(turn) < .2) {
                turn = Math.copySign(turn, .2);
            }
            else if(Math.abs(limelight.getX()) < 2) {
                finished = true;
            }
            */
            double turn;
            if(Math.abs(limelight.getX()) > 1.25) {
                turn = Math.copySign(.23, limelight.getX());
            }
            else {
                turn = 0;
                //finished = true;
            }
            
            drivetrain.arcadeDrive(0, turn);
            SmartDashboard.putNumber("Limelight Turn", turn);
        }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}