package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;

import edu.wpi.first.wpilibj.VictorSP;

public class Climber {
	VictorSP climberMotor;

	public Climber() {
		climberMotor = new VictorSP(Wiring.CLIMBER_MOTOR);
	}

	public void climb(double climbPower) {
		climberMotor.set(-Math.max(0, climbPower));
	}
}
