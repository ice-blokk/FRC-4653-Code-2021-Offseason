// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autocommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Anglers;

public class AutoSetAngler extends CommandBase {
  private boolean setBottom = false;
  private double setPotentiometer = -1;
  private Anglers anglers;

  public AutoSetAngler(boolean setBottom, double setPotentiometer, Anglers anglers) {
    addRequirements(anglers);
    this.setBottom = setBottom;
    this.setPotentiometer = setPotentiometer;
  }
  
  public AutoSetAngler(boolean setBottom, Anglers anglers) {
    addRequirements(anglers);
    this.setBottom = setBottom;
    setPotentiometer = -1;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    if(setBottom && setPotentiometer == -1) {
      anglers.setDartSafely(-.6);
    }
    else if(!setBottom && setPotentiometer > -1) {
      if(setPotentiometer < anglers.getDartPot()) {
        anglers.setDartSafely(.6);
      }
      else {
        anglers.setDartSafely(-.6);
      }
    }
    else {
      anglers.setDartSafely(-.6);
    } // end of main if statement

  } // end of execute

  @Override
  public void end(boolean interrupted) {
    anglers.setDartSafely(0);
  }

  @Override
  public boolean isFinished() {
    if(setBottom && setPotentiometer == -1) {
      return anglers.getDartBottomLimit();
    }
    else if(!setBottom && setPotentiometer > -1) {
      if(setPotentiometer < anglers.getDartPot()) {
        return anglers.getDartPot() > setPotentiometer;
      }
      else {
        return anglers.getDartPot() < setPotentiometer;
      }
    }
    return true;
  } // end of isFinished
}
