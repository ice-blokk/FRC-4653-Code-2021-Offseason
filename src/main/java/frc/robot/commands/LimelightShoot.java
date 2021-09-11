package frc.robot.commands;

import edu.wpi.first.wpilibj.MedianFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.Limelight;

public class LimelightShoot extends CommandBase {

    private final Cannon cannon;
    private final Drivetrain drivetrain;

    private final Limelight limelight;
    
    private MedianFilter mFilter;

    public LimelightShoot(Cannon cannon, Drivetrain drivetrain) {
        this.cannon = cannon;
        addRequirements(cannon);

        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        limelight = drivetrain.getLimelight();
        mFilter = new MedianFilter(5);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(limelight.getValidTarget()) {

            /*
            double turn = Math.copySign(.22, limelight.getX() + 1);
            drivetrain.arcadeDrive(0, Math.abs(limelight.getX() + 1) > .5 ? turn : 0);
            */

            if(limelight.getArea() > 3.5) {
                //shoot(.18 * limelight.getArea() - .88);
            }
            else {
                //long shot here
               //shoot(-.9);
               shoot(-.7);
            }
            double calc = mFilter.calculate(limelight.getArea());
            SmartDashboard.putNumber("Median Filter Limelight Area", calc);
        }
    }

    private void shoot(double speed) {
        cannon.pidShoot(0.7 * speed, speed);
        if(cannon.getBottomVelocity() < -4100 * Math.abs(speed) && cannon.getBottomVelocity() > -4300 * Math.abs(speed)) {
            cannon.setFeeder(-1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
        cannon.shoot(false);
        cannon.setFeeder(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}