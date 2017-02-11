package com.coderedrobotics;

import com.coderedrobotics.libs.PIDControllerAIAO;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	CANTalon shooterFollower;
	CANTalon shooter;
	CANTalon ballFeeder;
	PIDControllerAIAO pid;

	public Shooter() {
		shooter = new CANTalon(Wiring.SHOOTER_MOTOR_SHOOTER);
		shooter.setPID(Calibration.SHOOTER_P, Calibration.SHOOTER_I, Calibration.SHOOTER_D);
		shooter.setFeedbackDevice(FeedbackDevice.QuadEncoder);

		shooterFollower = new CANTalon(Wiring.SHOOTER_MOTOR_FOLLOWER);
		shooterFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
		shooterFollower.set(Wiring.SHOOTER_MOTOR_SHOOTER);

		ballFeeder = new CANTalon(Wiring.SHOOTER_MOTOR_FEEDER);
		ballFeeder.setPID(Calibration.FEEDER_P, Calibration.FEEDER_I, Calibration.FEEDER_D);

		shooter.reverseSensor(true);
		shooterFollower.reverseSensor(true);

		shooter.configPeakOutputVoltage(0, 6);
		shooterFollower.configPeakOutputVoltage(0, 6);
		ballFeeder.configPeakOutputVoltage(0, 6);

		shooter.configEncoderCodesPerRev(0);

		shooter.setProfile(0);
		shooter.setPID(0, 0, 0);
		shooter.changeControlMode(TalonControlMode.Speed);

		SmartDashboard.putNumber("Shooter Setpoint", 0);
		SmartDashboard.putNumber("Set P", 0);
		SmartDashboard.putNumber("Set I", 0);
		SmartDashboard.putNumber("Set D", 0);
		SmartDashboard.putNumber("Set F", 0);
	}

	public void spinUpShooter() {
		shooter.setSetpoint(SmartDashboard.getNumber("Shooter Setpoint", 0));
		shooter.setP(SmartDashboard.getNumber("Set P", 0));
		shooter.setI(SmartDashboard.getNumber("Set I", 0));
		shooter.setD(SmartDashboard.getNumber("Set D", 0));
		shooter.setF(SmartDashboard.getNumber("Set F", 0));
		SmartDashboard.putNumber("Display Error", shooter.getError());
	}

	public boolean isSpunUp() {
		return Math.abs(shooter.getClosedLoopError()) < 20;
	}

	public void stopShooter() {
		shooter.setSetpoint(0);
		ballFeeder.setSetpoint(0);
	}

	public void feedShooter() {
		ballFeeder.setSetpoint(SmartDashboard.getNumber("Ball Feeder Setpoint", 0));
	}
}