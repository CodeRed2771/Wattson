package com.coderedrobotics;

import com.coderedrobotics.libs.PIDControllerAIAO;
import com.coderedrobotics.libs.PIDVirtualCANTalonWrapper;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
	CANTalon ballFeeder;
	PIDVirtualCANTalonWrapper shooter;
	PIDControllerAIAO pid;

	public Shooter() {
		ballFeeder = new CANTalon(Wiring.SHOOTER_MOTOR_FEEDER);
		shooter = new PIDVirtualCANTalonWrapper(Wiring.SHOOTER_MOTOR_SHOOTER, "Shooter", true);
		shooter.setPID(Calibration2016.SHOOTER_P, Calibration2016.SHOOTER_I, Calibration2016.SHOOTER_D);
		shooter.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		ballFeeder.setFeedbackDevice(FeedbackDevice.QuadEncoder);

		pid = new PIDControllerAIAO(1, 0, 0, ballFeeder, ballFeeder, true, "asdf");
		
		shooter.reverseSensor(true);
		ballFeeder.reverseSensor(true);

		shooter.configNominalOutputVoltage(0,6);
		ballFeeder.configNominalOutputVoltage(0,6);
		
		shooter.configEncoderCodesPerRev(0);
		ballFeeder.configEncoderCodesPerRev(0);

		shooter.setProfile(0);
		shooter.setPID(0, 0, 0);
		shooter.changeControlMode(TalonControlMode.Speed);
		
		ballFeeder.setProfile(0);
		ballFeeder.setPID(0, 0, 0);
		ballFeeder.changeControlMode(TalonControlMode.Speed);
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
		shooter.setSetpoint(4);
		pid.setSetpoint(1);
	}

	public boolean isSpunUp() {
		return Math.abs(shooter.getClosedLoopError()) < 20;
	}

	public void stopShooter() {
		shooter.setSetpoint(0);
	}

	public void feedShooter() {
		ballFeeder.setSetpoint(SmartDashboard.getNumber("Ball Feeder Setpoint", 0));
	}
}
