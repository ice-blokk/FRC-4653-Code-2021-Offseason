// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autocommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Anglers;

/**
 * Auto Command to set the cannon within frame perimeter (the robot's position before the math)
 */
public class AutoSetWithinFramePerimeter extends CommandBase {

  private Anglers anglers;
  private double defaultPotValue;

  public AutoSetWithinFramePerimeter(Anglers anglers) {
    this.anglers = anglers;
    addRequirements(this.anglers);

    defaultPotValue = 140; // The potentiometer value for the angle the cannon needs to be
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    anglers.setDartSafely(.4);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    if(anglers.getDartPot() < defaultPotValue) {
      return false;
    }
    else {
      return true;
    }
  }
}
