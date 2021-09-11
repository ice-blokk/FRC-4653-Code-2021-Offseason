package frc.robot.commands.defaultcommands;

import java.util.function.BooleanSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Anglers;

public class DefaultAngler extends CommandBase {

    private final Anglers anglers;
    private final BooleanSupplier up, down;

    public DefaultAngler(BooleanSupplier up, BooleanSupplier down, Anglers anglers) {
        this.anglers = anglers;
        addRequirements(anglers);

        this.up = up;
        this.down = down;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(up.getAsBoolean()) {
            if(!anglers.setDartSafely(.75)) {
                //stop based on lead encoder for climbing
                anglers.setLeadSafely(.75);
            }
        }
        else if(down.getAsBoolean()) {
            if(!anglers.setLeadSafely(-.75)) {
                anglers.setDartSafely(-.75);
            }
        }
        else {
            anglers.setDartSafely(0);
            anglers.setLeadSafely(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        anglers.setDartSafely(0);
        anglers.setLeadSafely(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}