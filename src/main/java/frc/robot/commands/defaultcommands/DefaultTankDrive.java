// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.defaultcommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DefaultTankDrive extends CommandBase {
  /** Creates a new DefaultTankDrive. */
  private final Drivetrain drivetrain;

  private final DoubleSupplier leftSpeed, rightSpeed;

  public DefaultTankDrive(DoubleSupplier leftSpeed, DoubleSupplier rightSpeed, Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;
    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      double ls = leftSpeed.getAsDouble();
      double rs = rightSpeed.getAsDouble();

      ls = Math.pow(ls,3) * 0.5;
      rs = Math.pow(rs,3) * 0.5;

      drivetrain.tankDrive(ls,rs,false);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
