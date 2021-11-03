package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Cannon;

public class BasicShoot extends CommandBase {

    private final Cannon cannon;

    public BasicShoot(Cannon cannon) {
        this.cannon = cannon;
        addRequirements(cannon);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double x = -1;
        cannon.pidShoot(.75 * x, .85 * x);
        if(cannon.getTopVelocity() <= Constants.SHOOTER_MAX_VELOCITY * .65 * x) {
            cannon.setFeeder(-1);
        }

    }

    @Override
    public void end(boolean interrupted) {
        cannon.shoot(false);
        cannon.setFeeder(0);
    }

    @Override
    public boolean isFinished() {
        return !true;
        //Emerson did this, 4 on AP CSA Exam
    }
}