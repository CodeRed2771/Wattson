package com.coderedrobotics;

import com.coderedrobotics.libs.PIDControllerAIAO;
import com.ctre.CANTalon;

public class Shooter {
	CANTalon ballFeeder;
	CANTalon shooter;
	PIDControllerAIAO pid;

	public Shooter() {
		ballFeeder = new CANTalon(Wiring.SHOOTER_MOTOR_FEEDER);
		shooter = new CANTalon(Wiring.SHOOTER_MOTOR_SHOOTER);
		shooter.setPID(Calibration2016.SHOOTER_P, Calibration2016.SHOOTER_I, Calibration2016.SHOOTER_D);
	}

	public void spinUpShooter() {
		
	}

	public boolean isSpunUp() {
		return Math.abs(shooter.getClosedLoopError()) < 20;
	}

	public void stopShooter() {
		
	}

	public void feedShooter() {
	}
}
