// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ResetStuff extends CommandBase {

  private Drivetrain drivetrain;
  private boolean finished;
  public ResetStuff(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    finished = false;
  }

  @Override
  public void initialize() {
    finished = false;
  }

  @Override
  public void execute() {
    drivetrain.resetOdometry(new Pose2d(0, 0, new Rotation2d(0)));;
    drivetrain.zeroHeading();
    finished = true;
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return finished;
  }
}
