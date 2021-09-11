package frc.robot.commands.defaultcommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cannon;

public class DefaultCannon extends CommandBase {

    private final Cannon cannon;

    private final BooleanSupplier reverse, forward;

    private boolean sensorWasTriggered;
    private Timer indexTimer;

    public DefaultCannon(BooleanSupplier reverse, BooleanSupplier forward, Cannon cannon) {
        this.cannon = cannon;
        addRequirements(cannon);
        
        this.reverse = reverse;
        this.forward = forward;

        indexTimer = new Timer();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        cannon.shoot(false);
        if(reverse.getAsBoolean()) {
            cannon.setFeeder(1);
        }
        else if (forward.getAsBoolean()) {
            cannon.setFeeder(-1);
        }
        else {
            if(!cannon.getFrontSensor()) {
                cannon.setFeeder(-1);
                indexTimer.reset();
                indexTimer.start();
                sensorWasTriggered = true;
            }
            else if(sensorWasTriggered) {
                if(indexTimer.get() > .17) {
                    indexTimer.stop();
                    indexTimer.reset();
                    sensorWasTriggered = false;
                    cannon.setFeeder(0);
                }
            }
            else {
                cannon.setFeeder(0);
            }
        }

    }

    @Override
    public void end(boolean interrupted) {
        cannon.shoot(false);
        cannon.setFeeder(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}