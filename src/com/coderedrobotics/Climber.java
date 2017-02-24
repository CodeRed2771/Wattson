package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;

import edu.wpi.first.wpilibj.VictorSP;

public class Climber {
	VictorSP climberMotor;
	boolean isClimbing = false;
//	CurrentBreaker climberBreaker;

	public Climber() {
		climberMotor = new VictorSP(Wiring.CLIMBER_MOTOR);
//		climberBreaker = new CurrentBreaker(null, Wiring.CLIMBER_PDP, Calibration.CLIMBER_CURRENT_THRESHOLD,
//				Calibration.CLIMBER_CURRENT_TIMEOUT, Calibration.CLIMBER_CURRENT_IGNORE_DURATION);
	}

	public void climb(double climbPower) {
		climberMotor.set(climbPower);
	//	isClimbing = true;
	}

//	public void stop() {
//		climberMotor.set(0);
//		isClimbing = false;
//	}
//
//	public boolean isStalled() {
//		return climberBreaker.tripped();
//	}

//	public void tick() {
//		if (isClimbing && isStalled()) {
//			stop();
//		}
//	}
}
