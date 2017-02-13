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
	boolean isFeeding = false;

	public Shooter() {
		shooter = new CANTalon(Wiring.SHOOTER_MOTOR_SHOOTER);
		shooter.setPID(Calibration.SHOOTER_P, Calibration.SHOOTER_I, Calibration.SHOOTER_D);
		shooter.setF(Calibration.SHOOTER_F);
		shooter.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		shooter.configEncoderCodesPerRev(256); // RANDOM AT THE MOMENT
		shooter.setProfile(0);
		shooter.changeControlMode(TalonControlMode.Speed);
		shooter.reverseSensor(true);
		shooter.configPeakOutputVoltage(0, 13);

		shooterFollower = new CANTalon(Wiring.SHOOTER_MOTOR_FOLLOWER);
		shooterFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
		shooterFollower.set(Wiring.SHOOTER_MOTOR_SHOOTER);

		ballFeeder = new CANTalon(Wiring.SHOOTER_MOTOR_FEEDER);
		ballFeeder.setPID(Calibration.FEEDER_P, Calibration.FEEDER_I, Calibration.FEEDER_D);
		ballFeeder.setF(Calibration.FEEDER_F);
		ballFeeder.configPeakOutputVoltage(0, 13);

		// pid = new PIDControllerAIAO(1, 0, 0, ballFeeder, ballFeeder, true,"asdf");

		SmartDashboard.putNumber("Shooter Setpoint", Calibration.SHOOTER_SETPOINT);
		SmartDashboard.putNumber("Shooter P", Calibration.SHOOTER_P);
		SmartDashboard.putNumber("Shooter I", Calibration.SHOOTER_I);
		SmartDashboard.putNumber("Shooter D", Calibration.SHOOTER_D);
		SmartDashboard.putNumber("Shooter F", Calibration.SHOOTER_F);

		SmartDashboard.putNumber("Feeder Setpoint", Calibration.FEEDER_SETPOINT);
		SmartDashboard.putNumber("Feeder P", Calibration.FEEDER_P);
		SmartDashboard.putNumber("Feeder I", Calibration.FEEDER_I);
		SmartDashboard.putNumber("Feeder D", Calibration.FEEDER_D);
		SmartDashboard.putNumber("Feeder F", Calibration.FEEDER_F);

	}

	public void spinUpShooter() {
		isShooting = true;
		shooter.set(Calibration.SHOOTER_SETPOINT);
	}

	public boolean isSpunUp() {
		return Math.abs(shooter.getClosedLoopError()) < 20;
	}

	public void stopShooter() {
		stopFeeder();
		shooter.setSetpoint(0);
		ballFeeder.setSetpoint(0);
		isShooting = false;
	}

	public void feedShooter() {
		if (!isFeeding && isSpunUp()) {
			ballFeeder.setSetpoint(Calibration.FEEDER_SETPOINT);
			isFeeding = true;
		}
	}
	
	public void stopFeeder() {
		ballFeeder.set(0);
		isFeeding = false;
	}

	public void tick() {
		SmartDashboard.putBoolean("SHOOTER SPUN UP", isSpunUp());
		
		if (isShooting) {
			shooter.setSetpoint(SmartDashboard.getNumber("Shooter Setpoint", Calibration.SHOOTER_SETPOINT));
			shooter.setP(SmartDashboard.getNumber("Shooter P", Calibration.SHOOTER_P));
			shooter.setI(SmartDashboard.getNumber("Shooter I", Calibration.SHOOTER_I));
			shooter.setD(SmartDashboard.getNumber("Shooter D", Calibration.SHOOTER_D));
			shooter.setF(SmartDashboard.getNumber("Shooter F", Calibration.SHOOTER_F));

			SmartDashboard.putNumber("Shooter Error", shooter.getError());
			SmartDashboard.putNumber("Shooter Get", shooter.get());
		}
		
		if (isFeeding) {
			ballFeeder.setSetpoint(SmartDashboard.getNumber("Ball Feeder Setpoint", Calibration.FEEDER_SETPOINT));
			ballFeeder.setP(SmartDashboard.getNumber("Ball Feeder P", Calibration.FEEDER_P));
			ballFeeder.setI(SmartDashboard.getNumber("Ball Feeder I", Calibration.FEEDER_I));
			ballFeeder.setD(SmartDashboard.getNumber("Ball Feeder D", Calibration.FEEDER_D));
			ballFeeder.setF(SmartDashboard.getNumber("Ball Feeder F", Calibration.FEEDER_F));

			SmartDashboard.putNumber("Ball Feeder Error", ballFeeder.getError());
			SmartDashboard.putNumber("Ball Feeder Get", ballFeeder.get());

		}
	}
}