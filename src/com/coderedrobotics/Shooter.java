package com.coderedrobotics;

import com.coderedrobotics.libs.PIDControllerAIAO;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	CANTalon shooterFollower;
	CANTalon shooter;
	CANTalon ballFeeder;
	Agitator agitator;
	PIDControllerAIAO pid;
	boolean isShooting = false;
	boolean isFeeding = false;
	double feederStartTime;

	public Shooter() {
		shooter = new CANTalon(Wiring.SHOOTER_MOTOR_SHOOTER);
		shooter.setPID(Calibration.SHOOTER_P, Calibration.SHOOTER_I, Calibration.SHOOTER_D);
		shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		shooter.configNominalOutputVoltage(0.0f, 0.0f);
		shooter.configPeakOutputVoltage(13, 0);
		shooter.configEncoderCodesPerRev(4096);
		shooter.setProfile(0);
		shooter.setPID(Calibration.SHOOTER_P, Calibration.SHOOTER_I, Calibration.SHOOTER_D);
		shooter.changeControlMode(TalonControlMode.Speed);
		
		shooter.setF(Calibration.SHOOTER_F);

	
		shooterFollower = new CANTalon(Wiring.SHOOTER_MOTOR_FOLLOWER);
		shooterFollower.changeControlMode(TalonControlMode.Follower);
		shooterFollower.set(shooter.getDeviceID());
		

		ballFeeder = new CANTalon(Wiring.SHOOTER_MOTOR_FEEDER);
		ballFeeder.setProfile(0);
		ballFeeder.changeControlMode(TalonControlMode.Speed);
		ballFeeder.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		ballFeeder.setPID(Calibration.FEEDER_P, Calibration.FEEDER_I, Calibration.FEEDER_D);
		ballFeeder.setF(Calibration.FEEDER_F);
		ballFeeder.configPeakOutputVoltage(13, -13);
		ballFeeder.configNominalOutputVoltage(0.0f, 0.0f);
		//ballFeeder.enable();

		agitator = new Agitator();
	
		// pid = new PIDControllerAIAO(1, 0, 0, ballFeeder, ballFeeder,
		// true,"asdf");

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
		return Math.abs(shooter.getClosedLoopError()) < 5000;
		
	}

	public void stopShooter() {
		stopFeeder();
		shooter.set(0);
		isShooting = false;
	}

	public void feedShooter() {
		if (!isFeeding) { //add spun up code
			ballFeeder.set(-Calibration.FEEDER_SETPOINT);
			feederStartTime = System.currentTimeMillis();
			agitator.start();
			isFeeding = true;
		}
	}

	public void stopFeeder() {
		ballFeeder.set(0);
		agitator.stop();
		isFeeding = false;
	}

	public void toggleShooter() {
		if (isShooting) {
			stopShooter();
		} else {
			spinUpShooter();
		}
	}

	public void tick() {
		SmartDashboard.putBoolean("SHOOTER SPUN UP", isSpunUp());
		SmartDashboard.putBoolean("iShooting", isShooting);
		SmartDashboard.putNumber("Shooter 1",shooter.getSetpoint());
		SmartDashboard.putNumber("Shooter 2", shooterFollower.getSetpoint());
		
		
		if (isShooting) {
			shooter.set(SmartDashboard.getNumber("Shooter Setpoint", Calibration.SHOOTER_SETPOINT));
			
			shooter.setP(SmartDashboard.getNumber("Shooter P", Calibration.SHOOTER_P));
			shooter.setI(SmartDashboard.getNumber("Shooter I", Calibration.SHOOTER_I));
			shooter.setD(SmartDashboard.getNumber("Shooter D", Calibration.SHOOTER_D));
			shooter.setF(SmartDashboard.getNumber("Shooter F", Calibration.SHOOTER_F));

			SmartDashboard.putNumber("Shooter Error", shooter.getError());
			SmartDashboard.putNumber("Shooter Closed Loop Error", shooter.getClosedLoopError());
			SmartDashboard.putNumber("Shooter Get", shooter.get());
			SmartDashboard.putNumber("Shooter output voltage", shooter.getOutputVoltage());
			SmartDashboard.putNumber("Velocity", shooter.getEncVelocity());
		}

		if (isFeeding) {
			//ballFeeder.setSetpoint(SmartDashboard.getNumber("Ball Feeder Setpoint", Calibration.FEEDER_SETPOINT));
			ballFeeder.setP(SmartDashboard.getNumber("Ball Feeder P", Calibration.FEEDER_P));
			ballFeeder.setI(SmartDashboard.getNumber("Ball Feeder I", Calibration.FEEDER_I));
			ballFeeder.setD(SmartDashboard.getNumber("Ball Feeder D", Calibration.FEEDER_D));
			ballFeeder.setF(SmartDashboard.getNumber("Ball Feeder F", Calibration.FEEDER_F));

			SmartDashboard.putNumber("Ball Feeder Error", ballFeeder.getError());
			SmartDashboard.putNumber("Ball Feeder Get", ballFeeder.get());
			
			SmartDashboard.putNumber("System Millis: ", System.currentTimeMillis());
			SmartDashboard.putNumber("feederStartTime: ", feederStartTime);
			SmartDashboard.putNumber("Setpoint: ", ballFeeder.getSetpoint());
			
			if(System.currentTimeMillis()>(feederStartTime+500)){
				ballFeeder.set(Calibration.FEEDER_SETPOINT);
			}

		}
	}

}