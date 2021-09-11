package frc.robot.commands.autocommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AutoTurn extends CommandBase {

    private final Drivetrain drivetrain;

    private final double power, angle, startAngle;

    public AutoTurn(double angle, double power, Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);

        this.angle = angle;
        this.power = power;
        startAngle = drivetrain.getAngle();
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(0, power);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        if(drivetrain.getAngle() >= startAngle + angle && power > 0) {
            return true;
        }
        if(drivetrain.getAngle() <= startAngle - angle && power < 0) {
            return true;
        }
        return false;
    }
}