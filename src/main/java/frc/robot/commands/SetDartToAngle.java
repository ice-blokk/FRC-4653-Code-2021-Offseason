package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Anglers;
import frc.robot.util.Limelight;

/**
 * Test Command to set the dart to a certain angle
 */

// default frame perimeter 125 pot


public class SetDartToAngle extends CommandBase {
  
  private Anglers anglers;
  private Limelight limelight;

  public SetDartToAngle(Anglers anglers) {
    this.anglers = anglers;
    limelight = new Limelight();
    addRequirements(this.anglers);
  }

  @Override
  public void initialize() {
  }


  @Override
  public void execute() {

    double distance = anglers.getDistanceInInches(limelight.getArea());
    double angle = anglers.getAngleFromDistance(limelight.getArea(), distance, true) - 1;
    double thresholdAngle = anglers.getAngleFromDistance(limelight.getArea(), distance, false);
    double distanceThreshold = 175; //this unit is in inches
    // if the robot is too close to the target, it cannot shoot to the innter goal without arching the ball

    // Within Threshold
    if(distance > distanceThreshold) {
      if(anglers.potToDegrees() < angle - .5) {
        anglers.setDartSafely(.35);
      }
      else if (anglers.potToDegrees() > angle + .5) {
        anglers.setDartSafely(-.35);
      }
      else {
        anglers.setDartSafely(0);
      }
    }
    // Over threshold
    else {
      if(anglers.potToDegrees() < thresholdAngle - .5) {
        anglers.setDartSafely(.35);
      }
      else if (anglers.potToDegrees() > thresholdAngle + .5) {
        anglers.setDartSafely(-.35);
      }
      else {
        anglers.setDartSafely(0);
      }
      }

    SmartDashboard.putNumber("Angle from Distance", angle);
    SmartDashboard.putNumber("Distance from line", distance);
    
  }

  
  @Override
  public void end(boolean interrupted) {}


  @Override
  public boolean isFinished() {
    return false;
  }
}
