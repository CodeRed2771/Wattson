package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;
import com.coderedrobotics.libs.PWMController;
import com.ctre.CANTalon;

public class Pickup {

	private CANTalon sweeperMotor;
	private CANTalon sweeperFollower;
	private boolean pickingUp = false;
	
	public Pickup() {
		sweeperMotor = new CANTalon(Wiring.SWEEPER_MOTOR);
		sweeperFollower = new CANTalon(Wiring.SWEEPER_MOTOR_FOLLOWER);
		sweeperFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
		sweeperFollower.set(Wiring.SWEEPER_MOTOR);
	}

	public void sweeperStart() {
		sweeperMotor.set(1);
		pickingUp = true;
	}
	
	public void sweeperStop() {
		sweeperMotor.set(0);
		pickingUp = false;
	}
	
	public void sweeperReverse() {
		sweeperMotor.set(-1);
	}

	public void togglePickup() {
		if (pickingUp) {
			sweeperStop();
		} else
			sweeperStart();
	}

	public void tick() {
		
	}
}
