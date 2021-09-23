package frc.robot.commands.autopaths;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import frc.robot.subsystems.Drivetrain;

public final class TestPath {
    public static Trajectory getTraj(Drivetrain drivetrain) {
        Trajectory testPath = TrajectoryGenerator.generateTrajectory(
            drivetrain.getCurrentPose(),
            List.of(
              new Translation2d(2, 0)

            ),
            new Pose2d(4.3, 0, new Rotation2d()),
            drivetrain.getSlowConfig()
          );

        return testPath;
    }
    
}
