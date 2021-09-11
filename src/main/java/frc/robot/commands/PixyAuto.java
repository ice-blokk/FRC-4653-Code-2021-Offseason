package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.util.Pixy2Obj;

public class PixyAuto extends CommandBase {

    private final Drivetrain drivetrain;
    private final Intake intake;
    private final Pixy2Obj pixy;

    public PixyAuto(Drivetrain drivetrain, Intake intake) {
        this.drivetrain = drivetrain;
        this. intake = intake;
        addRequirements(drivetrain);
        addRequirements(intake);

        pixy = intake.getPixy();

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        pixy.updateValues();
        intake.setIntake(-1);

        SmartDashboard.putNumber("Pixy X", pixy.getPixyX());
        SmartDashboard.putNumber("Pixy Area", pixy.getPixyArea());
        SmartDashboard.putBoolean("Pixy Valid Target", pixy.pixySig(1));
        SmartDashboard.putNumber("Pixy Width", pixy.getWidth());
        SmartDashboard.putNumber("Pixy Height", pixy.getHeight());

        if(pixy.pixySig(1) && pixy.getPixyArea() > 50){
            drivetrain.arcadeDrive(.4, (pixy.getPixyX() - 160) * 0.004);
        }
        else{
            drivetrain.arcadeDrive(0, 0);
        }
            

        /*if(pixy.pixySig(1)){
            if(pixy.getPixyArea() < 120){
                if(pixy.getPixyX() < 60)
                    drivetrain.arcadeDrive(0, -.25);
                else if(pixy.getPixyX() > 260)
                    drivetrain.arcadeDrive(0, .25);
                else
                    drivetrain.arcadeDrive(.4, 0);
            }
            else if(pixy.getPixyArea() < 800 && pixy.getPixyArea() > 120){
                if(pixy.getPixyX() < 130)
                    drivetrain.arcadeDrive(0, -.25);
                else if(pixy.getPixyX() > 190)
                    drivetrain.arcadeDrive(0, .25);
                else
                    drivetrain.arcadeDrive(.4, 0);
            }
            else if(pixy.getPixyArea() < 3000 && pixy.getPixyArea() > 800){
                if(pixy.getPixyX() < 148)
                    drivetrain.arcadeDrive(0, -.25);
                else if(pixy.getPixyX() > 172)
                    drivetrain.arcadeDrive(0, .25);
                else
                    drivetrain.arcadeDrive(.4, 0);
            }
            else if(pixy.getPixyArea() < 50000 && pixy.getPixyArea() > 3000)
                drivetrain.arcadeDrive(.4, 0);
            else
                drivetrain.arcadeDrive(0, 0);
        }*/
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
