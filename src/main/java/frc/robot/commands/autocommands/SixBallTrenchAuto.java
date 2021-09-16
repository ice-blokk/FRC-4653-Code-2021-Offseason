// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autocommands;

import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.BasicShoot;
import frc.robot.commands.LimelightAngle;
import frc.robot.commands.LimelightDrive;
import frc.robot.commands.SetDartToAngle;
import frc.robot.subsystems.Anglers;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SixBallTrenchAuto extends SequentialCommandGroup {
  /** Creates a new TestAutoCommand. */
  public SixBallTrenchAuto(Drivetrain drivetrain, Anglers anglers, RamseteCommand ramseteCommand, Intake intake) {

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
          /*        
        new ParallelCommandGroup(
          new AutoTurn(-30, -.45, drivetrain),
          new RunCommand(() -> anglers.setDartSafely(-.5), anglers).withTimeout(1.25).andThen(() -> anglers.setDartSafely(0))
        ),

        new ParallelCommandGroup(
          new LimelightAngle(drivetrain.getLimelight(), anglers),
          new LimelightDrive(drivetrain)
        ),
        */

        /*
        new ParallelCommandGroup(
          //shoot thing
        ),
        */

        /*
        new ParallelCommandGroup(
          new AutoTurn(0, .5, drivetrain),
          new RunCommand(() -> anglers.setDartSafely(-.3), anglers).withTimeout(.75).andThen(() -> anglers.setDartSafely(0)),
          new RunCommand(() -> intake.setArm(.65), intake).withTimeout(.2)
        ),
        */
        new ParallelDeadlineGroup(
          ramseteCommand,
          new RunCommand(() -> intake.setIntake(-.4))
        )
        
    );
  }
}
