package frc.robot.commands.defaultcommands;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix.motorcontrol.SensorTerm;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cannon;

public class DefaultCannon extends CommandBase {

    public static boolean isFull;

    private final Cannon cannon;

    private final BooleanSupplier reverse, forward, climb, unclimb;
    private boolean sensorWasTriggered;
    private Timer indexTimer;

    public DefaultCannon(BooleanSupplier reverse, BooleanSupplier forward, BooleanSupplier climb, BooleanSupplier unclimb, Cannon cannon) {
        this.cannon = cannon;
        addRequirements(cannon);
        
        this.reverse = reverse;
        this.forward = forward;
        this.climb = climb;
        this.unclimb = unclimb;

        sensorWasTriggered = false;
        indexTimer = new Timer();
        isFull = false;
    }

    @Override
    public void initialize() {
        sensorWasTriggered = false;
    }

    @Override
    public void execute() {

        cannon.shoot(false);
        if(climb.getAsBoolean()) {
            cannon.setClimber(-1);
        }
        else if(unclimb.getAsBoolean()) {
            cannon.setClimber(1);
        }
        else  {
            cannon.setClimber(0);
            if(reverse.getAsBoolean()) {
                cannon.setFeeder(.6);
                isFull = false;
            }
            else if(forward.getAsBoolean()) {
                cannon.setFeeder(-.6);
            }







/*
if sees balll run thing
if stop seeing ball, stop thing
if still sees ball after 1 sec, stop running thing

*/

            else if(cannon.getShooterSensor() && !isFull) {
                //start of brady's deleted code
                sensorWasTriggered = false;
                indexTimer.reset();
                indexTimer.stop();
                if(!cannon.getIntakeSensor() && !sensorWasTriggered) {
                    indexTimer.start();
                    if(indexTimer.get() < .25) {
                        cannon.setFeeder(-1);
                    }
                    else {
                        sensorWasTriggered = true;
                        cannon.setFeeder(0);
                    }
                }
                // end of BRADY'S deleted code
                else {
                    cannon.setFeeder(0);
                } 
                isFull = false;
            }
            else {
                isFull = true;
                cannon.setFeeder(0);
            }
        }
        SmartDashboard.putNumber("Index Timer", indexTimer.get());

        //brady's deleted code
                       /* if(!cannon.getIntakeSensor()) {
                    indexTimer.start();
                    cannon.setFeeder(-1);
                    if(indexTimer.get() < 1.5){
                        if(!cannon.getIntakeSensor()){
                            cannon.setFeeder(-1);
                        }
                        else if(cannon.getIntakeSensor()){
                            cannon.setFeeder(0);
                        }
                    }
                    if(indexTimer.get() > 1.5 && !cannon.getIntakeSensor()){
                        cannon.setFeeder(0);
                    }

                    else{
                        indexTimer.reset();
                    }
                }*/

    }

    @Override
    public void end(boolean interrupted) {
        cannon.shoot(false);
        cannon.setFeeder(0);
        cannon.setClimber(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}