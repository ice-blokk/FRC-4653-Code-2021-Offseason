package frc.robot.commands.autocommands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Limelight;

/**
 * Command to turn the drivetrain using the limelight
 */

public class AutoLimelightDrive extends CommandBase {

    private final Drivetrain drivetrain;

    private final Limelight limelight;

    boolean finished;
    PIDController pid;

    public AutoLimelightDrive(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        limelight = drivetrain.getLimelight();
        finished = false;
        pid = new PIDController(.35, .01, .01);
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
            double pidTurn = -pid.calculate(limelight.getX());

            if(Math.abs(pidTurn) < .1) {
                if(pidTurn < 0) {
                    pidTurn -= .15;
                }
                else {
                    pidTurn += .15;
                }
            }

            if(limelight.getValidTarget()) {
                drivetrain.arcadeDrive(0, pidTurn);
            }
            
        }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return limelight.getX() < .5 && limelight.getX() > -.5;
    }
}