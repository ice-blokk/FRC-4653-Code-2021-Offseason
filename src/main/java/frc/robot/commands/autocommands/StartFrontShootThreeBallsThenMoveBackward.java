// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autocommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.LimelightDrive;
import frc.robot.commands.ResetStuff;
import frc.robot.commands.BasicShoot;
import frc.robot.commands.LimelightAngle;
import frc.robot.commands.autocommands.AutoLimelightAngle;
import frc.robot.commands.autocommands.AutoLimelightDrive;
import frc.robot.commands.autocommands.AutoDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Anglers;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Intake;

public class StartFrontShootThreeBallsThenMoveBackward extends SequentialCommandGroup {
  /** Creates a new ShootThreeBallsThenMoveForward. */
  public StartFrontShootThreeBallsThenMoveBackward(Drivetrain drivetrain, Anglers anglers, Cannon cannon,
      Intake intake) {
    addCommands(

      new ResetStuff(drivetrain),

      new ParallelCommandGroup(
          new RunCommand(() -> anglers.setDartSafely(-.5), anglers).withTimeout(1.25).andThen(() -> anglers.setDartSafely(0))
        ),

      new ParallelCommandGroup(
          new LimelightAngle(drivetrain.getLimelight(), anglers),
          new LimelightDrive(drivetrain)
        ).withTimeout(2.5),
      
      new BasicShoot(cannon).withTimeout(3),
      
      new ParallelCommandGroup(
          new RunCommand(() -> anglers.setDartSafely(-.3), anglers).withTimeout(2.75).andThen(() -> anglers.setDartSafely(0)),
          new RunCommand(() -> intake.setArm(.6), intake).withTimeout(.2)
        ),
      
      new AutoDrive(.4, 20, drivetrain),

      new RunCommand(() -> drivetrain.arcadeDrive(0, 0), drivetrain)

    );
  }
}
