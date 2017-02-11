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
	boolean isShooting = false;

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
		SmartDashboard.putNumber("Shooter P", 0);
		SmartDashboard.putNumber("Shooter I", 0);
		SmartDashboard.putNumber("Shooter D", 0);
		SmartDashboard.putNumber("Shooter F", 0);
		
		SmartDashboard.putNumber("Feeder Setpoint", 0);
		SmartDashboard.putNumber("Feeder P", 0);
		SmartDashboard.putNumber("Feeder I", 0);
		SmartDashboard.putNumber("Feeder D", 0);
		SmartDashboard.putNumber("Feeder F", 0);

	}

	public void spinUpShooter() {
		isShooting = true;
	}

	public boolean isSpunUp() {
		return Math.abs(shooter.getClosedLoopError()) < 20;
	}

	public void stopShooter() {
		shooter.setSetpoint(0);
		ballFeeder.setSetpoint(0);
		isShooting = false;
	}

	public void feedShooter() {
		ballFeeder.setSetpoint(SmartDashboard.getNumber("Ball Feeder Setpoint", 0));
	}
	
	public void tick() {
		if (isShooting) {
			shooter.setSetpoint(SmartDashboard.getNumber("Shooter Setpoint", 0));
			shooter.setP(SmartDashboard.getNumber("Shooter P", 0));
			shooter.setI(SmartDashboard.getNumber("Shooter I", 0));
			shooter.setD(SmartDashboard.getNumber("Shooter D", 0));
			shooter.setF(SmartDashboard.getNumber("Shooter F", 0));
			SmartDashboard.putNumber("Shooter Error", shooter.getError());

			ballFeeder.setSetpoint(SmartDashboard.getNumber("Ball Feeder Setpoint", 0));
			ballFeeder.setP(SmartDashboard.getNumber("Ball Feeder P", 0));
			ballFeeder.setI(SmartDashboard.getNumber("Ball Feeder I", 0));
			ballFeeder.setD(SmartDashboard.getNumber("Ball Feeder D", 0));
			ballFeeder.setF(SmartDashboard.getNumber("Ball Feeder F", 0));
			SmartDashboard.putNumber("Ball Feeder Error", ballFeeder.getError());

		}
	}
}