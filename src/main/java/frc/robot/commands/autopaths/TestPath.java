package frc.robot.commands.autopaths;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;

public final class TestPath {
    public static Trajectory getTraj(TrajectoryConfig config) {
        Trajectory testPath = TrajectoryGenerator.generateTrajectory(
            new Pose2d(),
            List.of(
              new Translation2d(1.2, 1),
              //new Translation2d(1.5,1.5),
              new Translation2d(2, 1.3),
              new Translation2d(5.5, 1.1), // this and next waypoint go straight
              new Translation2d(5.8, -0.1),
            //new Translation2d(6.2, -.3),
              new Translation2d(7.5, -.4), 
              new Translation2d(7.2, 1),
              new Translation2d(6.2, 1.1),
              new Translation2d(5.7, 0), // this and next waypoint go straight
              new Translation2d(2.2, 0),
              new Translation2d(1.6, .8)
            ),
            new Pose2d(0, 1.6, new Rotation2d(Math.toRadians(180))),
            config
          );

        return testPath;
    }
    
}
