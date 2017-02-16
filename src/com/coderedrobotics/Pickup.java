package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;
import com.coderedrobotics.libs.PWMController;

public class Pickup {

	private final PWMController sweeperMotor;
	private boolean pickingup = false;

	public Pickup() {
		sweeperMotor = new PWMController(Wiring.SWEEPER_MOTOR, false);
	}

	public void sweeperStart() {
		sweeperMotor.set(1);

	}

	public void sweeperReverse() {
		sweeperMotor.set(-1);

	}

	public void sweeperStop() {
		sweeperMotor.set(0);

	}

	public void tick() {
	}
}
