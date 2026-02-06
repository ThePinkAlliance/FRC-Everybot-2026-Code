package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.ClimbConstatns.*;

public class ClimberSubsystem extends SubsystemBase {
    private final SparkMax climberMotor;

    private int climberCounter = 0;
    private boolean isClimbing = false;

    public ClimberSubsystem() {
        climberMotor = new SparkMax(CLIMBER_MOTOR_ID, MotorType.kBrushed);

        SparkMaxConfig climbConfig = new SparkMaxConfig();
        climbConfig.smartCurrentLimit(CLIMBER_MOTOR_CURRENT_LIMIT);
        climbConfig.idleMode(IdleMode.kBrake);
        climberMotor.configure(climbConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setClimber(double power) {
        isClimbing = true;
        climberCounter = 100; // number of periodic cycles to run motor
        climberMotor.set(power);
    }

    public void stop() {
        climberMotor.set(0);
        isClimbing = false;
    }

    @Override
    public void periodic() {
        if (isClimbing) {
            climberCounter--;
            if (climberCounter <= 0) {
                stop();
            }
        }
    }
}

