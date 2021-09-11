package frc.robot.commands.defaultcommands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class DefaultIntake extends CommandBase {

    private final Intake intake;

    private final BooleanSupplier suckIn;
    private final BooleanSupplier suckOut;
    private final BooleanSupplier up;
    private final BooleanSupplier down;

    public DefaultIntake(BooleanSupplier suckIn, BooleanSupplier suckOut, BooleanSupplier up, BooleanSupplier down, Intake intake) {
        this.intake = intake;
        addRequirements(intake);

        this.suckIn = suckIn;
        this.suckOut = suckOut;

        this.up = up;
        this.down = down;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if(suckIn.getAsBoolean()) {
            intake.setIntake(-1);
        }
        else if(suckOut.getAsBoolean()){
            intake.setIntake(1);
        }
        else if(suckIn.getAsBoolean() && suckOut.getAsBoolean()){
            intake.setIntake(0);
        }
        else {
            intake.setIntake(0);
        }

        if(up.getAsBoolean()){
            intake.setArm(.5);
        }
        else if(down.getAsBoolean()){
            intake.setArm(-.5);
        }
        else if (up.getAsBoolean() && down.getAsBoolean()){
            intake.setArm(0);
        }
        else{
            intake.setArm(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.setIntake(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}