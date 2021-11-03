package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Anglers extends SubsystemBase {

	private final CANSparkMax dart;
	private final WPI_TalonSRX lead;

	private final DigitalInput dartTopLimit, dartBottomLimit, leadFrontLimit, leadBackLimit;
	private final AnalogPotentiometer pot;

	public Anglers() {
		dart = new CANSparkMax(Constants.DART_ADDRESS, MotorType.kBrushless);
		dart.restoreFactoryDefaults();

		lead = new WPI_TalonSRX(Constants.LEAD_ADDRESS);

		dartTopLimit = new DigitalInput(Constants.DART_TOP_LIMIT_ADDRESS);
		dartBottomLimit = new DigitalInput(Constants.DART_BOTTOM_LIMIT_ADDRESS);
		leadFrontLimit = new DigitalInput(Constants.LEAD_FRONT_LIMIT_ADDRESS);
		leadBackLimit = new DigitalInput(Constants.LEAD_BACK_LIMIT_ADDRESS);

		// Range of potentiometer is (0 to 180) on port 0 (in AnalogInput)
		pot = new AnalogPotentiometer(Constants.DART_POTENTIOMETER_ADDRESS, 180, 0);
	}

	private void setDart(double pow) {
		dart.set(pow);
	}
	public boolean setDartSafely(double pow) {
		// Top Dart Limit (hall effect sensor) is broken rn
		if((pow > 0 && (/* getDartTopLimit() */ pot.get() < 140)) || (pow < 0 && getDartBottomLimit() && (getDartPot() > 12))) {
			setDart(pow);
			return true;
		}
		else {
			setDart(0);
			return false;
		}
	}
	public void setLead(double pow) {
		lead.set(pow);
	}
	public boolean setLeadSafely(double pow) {
		if((pow > 0 && !getLeadBackLimit()) || (pow < 0 && !getLeadFrontLimit())) {
			setLead(pow);
			return true;	
		}
		else {
			setLead(0);
			return false;
		}
	}

	public boolean getDartTopLimit() {
		return dartTopLimit.get();
	}
	public boolean getDartBottomLimit() {
		return dartBottomLimit.get();
	}
	public boolean getLeadFrontLimit() {
		return leadFrontLimit.get();
	}
	public boolean getLeadBackLimit() {
		return leadBackLimit.get();
	}

	public double getDartPot() {
		return pot.get();
	}

	
	// Getting distances and stuff //


	public double potToDegrees() {
		// Returns the degrees measure of the cannon's heading from the potentiometer value
		//return (27.0 / 64.0) * pot.get() - (81.0 / 16.0);
		//return (3.0 / 10.0) * pot.get() + 12;
		// The equations above are old equations
		return (3.0 / 10.0) * pot.get() + 15;
	}

	/**
	 * 
	 * @param area area retrieved from the limelight
	 * @return distance from the edge of the white tape in inches
	 */
	public double getDistanceInFeet(double area) {
		return Math.abs((-20.0/21.0) * (area) + 24.0); 
	}

	/**
	 * 
	 * @param area area retrieved from the limelight
	 * @return distance from the edge of the white tape in inches
	 */
	public double getDistanceInInches(double area) {
		return getDistanceInFeet(area) * 12.0;
	}

	/**
	 * Note: the threshold where the robot will be obstructed to shoot inner goals is at about 175 inches
	 * @param area area retrieved from the limelight
	 * @param distance distance in inches from the edge of the white line (note: this distance
	 *  is added to 31.25 inches to account for the width of the white tape - 2 inches - and the depth of the tower - 29.25 inches)
	 * @return angle (in degrees) for the cannon to point a line to the inner goal
	 */
	public double getAngleFromDistance(double area, double distance, boolean innerGoal) {
		double innerGoalCorrection = 0;
		
		if(innerGoal) {
			innerGoalCorrection = 31.25;
		}
		
		double theta = Math.atan(98.25 / (distance + innerGoalCorrection));
		theta = Math.toDegrees(theta);
		
		return theta;
	}


	@Override
	public void periodic() {
		SmartDashboard.putNumber("Dart Potentiometer", getDartPot());
		SmartDashboard.putNumber("Potentiometer to Degrees", potToDegrees());
	}
}