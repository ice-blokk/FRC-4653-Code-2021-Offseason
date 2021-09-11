// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drivetrain;

public class OdometryShoot extends CommandBase {
  Drivetrain drivetrain;
  Cannon cannon;
  BooleanSupplier bool;
  
  // Ideal robot x positions
  // veryFrontZone = 0, frontZone = 1.62, midZone = 3.14, backZone = 4.75;
  double xCoord;
  boolean inVeryFrontZone, inFrontZone, inMidZone, inBackZone;
  public OdometryShoot(Drivetrain drivetrain, Cannon cannon) {
    addRequirements(drivetrain, cannon);
    this.drivetrain = drivetrain;
    this.cannon = cannon;
    this.bool = bool;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    xCoord = drivetrain.getCurrentPose().getX();

    inVeryFrontZone = xCoord > -.5 && xCoord < 1;
    inFrontZone = xCoord > 1 && xCoord < 1.7;
    inMidZone = xCoord > 1.7 && xCoord < 3.2;
    inBackZone = xCoord > 3.2 && xCoord < 5;
    double x = -1;

    if(inVeryFrontZone) {
      cannon.pidShoot(.34 * x, .44 * x);
      if(cannon.getTopVelocity() <= Constants.SHOOTER_MAX_VELOCITY * .32 * x) {
          cannon.setFeeder(-1);
      }
    }
    else if(inFrontZone) {
      cannon.pidShoot(.5 * x, .7 * x);
        if(cannon.getTopVelocity() <= Constants.SHOOTER_MAX_VELOCITY * .48 * x) {
            cannon.setFeeder(-1);
        }
    }
    else if(inMidZone) {
      cannon.pidShoot(.6 * x, .7 * x);
        if(cannon.getTopVelocity() <= Constants.SHOOTER_MAX_VELOCITY * .58 * x) {
            cannon.setFeeder(-1);
        }
    }
    else if(inBackZone) {
      cannon.pidShoot(.7 * x, .8 * x);
        if(cannon.getTopVelocity() <= Constants.SHOOTER_MAX_VELOCITY * .68 * x) {
            cannon.setFeeder(-1);
        }
      } // end of shoot
      
  } // end of execute

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    cannon.shoot(false);
    cannon.setFeeder(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
