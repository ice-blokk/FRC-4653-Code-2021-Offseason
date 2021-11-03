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
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.BasicShoot;
import frc.robot.commands.LimelightAngle;
import frc.robot.commands.LimelightDrive;
import frc.robot.commands.PixyFindBall;
import frc.robot.commands.ResetStuff;
import frc.robot.commands.SetDartToAngle;
import frc.robot.commands.defaultcommands.DefaultCannon;
import frc.robot.subsystems.Anglers;
import frc.robot.subsystems.Cannon;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.util.Pixy2;
import frc.robot.util.Pixy2Obj;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SixBallTrenchAuto extends SequentialCommandGroup {
  /**
   * Creates a new TestAutoCommand.
   * 
   */
  public SixBallTrenchAuto(Drivetrain drivetrain, Anglers anglers, RamseteCommand ramseteCommand, Intake intake,
      Cannon cannon, Pixy2Obj pixy) {

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        
        // reset gyro and odometry
        new ResetStuff(drivetrain),

        // turn towards target and put cannon down
        new ParallelCommandGroup(
          new AutoTurn(-30, -.35, drivetrain),
          new RunCommand(() -> anglers.setDartSafely(-.5), anglers).withTimeout(1)
        ),

        // aim using limelight
        new ParallelCommandGroup(
          new LimelightDrive(drivetrain), 
          new LimelightAngle(drivetrain.getLimelight(), anglers)  
        ).withTimeout(2),

        // shoot here
        new BasicShoot(cannon).withTimeout(3),

        // put cannon all the way down, turn towards trench, put down intake
        new ParallelCommandGroup(
          new RunCommand(() -> anglers.setDartSafely(-.3), anglers).withTimeout(2.75).andThen(() -> anglers.setDartSafely(0)),
          new AutoTurn(-10, .45, drivetrain),
          new RunCommand(() -> intake.setArm(.5), intake).withTimeout(.2)
        ),
      
        
        // stop intake (just in case it doesn't stop int he parallel)
        new RunCommand(() -> anglers.setDartSafely(0), anglers).withTimeout(.1),
      
        // drive towards trench
        new RunCommand(() -> drivetrain.arcadeDrive(.4, 0), drivetrain)
        .withTimeout(1.8)
        .andThen(() -> drivetrain.arcadeDrive(0, 0)),

        // intake 3 balls using pixy
        new ParallelDeadlineGroup(
          new PixyFindBall(drivetrain, cannon, pixy),
          new RunCommand(() -> intake.setIntake(-1), intake)
        ),

        new ParallelDeadlineGroup(
          new PixyFindBall(drivetrain, cannon, pixy),
          new RunCommand(() -> intake.setIntake(-1), intake)
        ),

        // drive forward to see other final ball
        new RunCommand(() -> drivetrain.arcadeDrive(.4, 0), drivetrain)
        .withTimeout(2.0)
        .andThen(() -> drivetrain.arcadeDrive(0, 0)),

        new ParallelDeadlineGroup(
          new PixyFindBall(drivetrain, cannon, pixy),
          new RunCommand(() -> intake.setIntake(-1), intake)
        ).andThen(() -> intake.setIntake(0)),


        
        // turn towards target
        new AutoTurn(-8, -.5, drivetrain),

        // aim using limelight
        new ParallelCommandGroup(
          new LimelightAngle(drivetrain.getLimelight(), anglers),
          new LimelightDrive(drivetrain)
        ).withTimeout(2),

        //shoot
        new BasicShoot(cannon).withTimeout(3),

        new RunCommand(() -> drivetrain.arcadeDrive(0, 0), drivetrain)
        
       
    );
    
  }
}
