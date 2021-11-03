package frc.robot.commands.autocommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Anglers;
import frc.robot.util.Limelight;

/**
 * Command to angle the cannon using the limelight
 */
public class AutoLimelightAngle extends CommandBase {

    private final Anglers anglers;

    private final Limelight limelight;

    boolean finished = false;

    public AutoLimelightAngle(Limelight limelight, Anglers anglers) {
        this.anglers = anglers;
        addRequirements(anglers);

        this.limelight = limelight;
    }

    @Override
    public void initialize() {
        finished = false;
    }

    @Override
    public void execute() {

        if(limelight.getValidTarget()) {
            //if(limelight.getArea() > 2) {;
                if(limelight.getY() < -2) {
                    if(anglers.getDartBottomLimit()) {
                        anglers.setDartSafely(-.3);
                    }
                }
                else if(limelight.getY() > -1) {
                    if(anglers.getDartTopLimit()) {
                        anglers.setDartSafely(.3);
                    }
                }
                else {
                    anglers.setDartSafely(0);
                    finished = true;
                }
            //}
            /*
            else {
                //Long shot here
                finished = anglers.setDartSafely(.22);
            }
            */
        }



        /*
        if(limelight.getValidTarget()) {
            if(limelight.getArea() > 2) {
                double heightAdjustDegrees = (limelight.getVertical() / 2) * (59.6 / 320) / 2;
                if(limelight.getY() + heightAdjustDegrees - 1 < -.4) {
                    if(anglers.getDartBottomLimit()) {
                        anglers.setDartSafely(-.35);
                    }
                }
                else if(limelight.getY() + heightAdjustDegrees - 1 > .4) {
                    if(anglers.getDartTopLimit()) {
                        anglers.setDartSafely(.35);
                    }
                }
                else {
                    anglers.setDartSafely(0);
                    finished = true;
                }
            }
            else {
                //Long shot here
                finished = anglers.setDartSafely(.22);
            }
        }
        */
        }

    @Override
    public void end(boolean interrupted) {
        anglers.setDartSafely(0);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
