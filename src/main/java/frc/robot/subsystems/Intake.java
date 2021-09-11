package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Pixy2Obj;

public class Intake extends SubsystemBase {
	
	private final WPI_TalonSRX arm, intake;

	private final Pixy2Obj pixy;


	public Intake() {
		arm = new WPI_TalonSRX(Constants.INTAKE_ARM_ADDRESS);
		intake = new WPI_TalonSRX(Constants.INTAKE_SUCKER_ADDRESS);

		pixy = new Pixy2Obj();
	}

	public void setArm(double power) {
		arm.set(power);
	}
	public void setIntake(double power) {
		intake.set(power);
	}

	public Pixy2Obj getPixy(){
        return pixy;
    }

	@Override
	public void periodic() {
		
	}

}