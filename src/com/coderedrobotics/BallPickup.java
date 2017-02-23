package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;
import com.coderedrobotics.libs.PWMController;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallPickup {

	private CANTalon sweeperMotor;
	private CANTalon sweeperFollower;
	private boolean pickingUp = false;
	
	public BallPickup() {
		sweeperMotor = new CANTalon(Wiring.SWEEPER_MOTOR);
		sweeperFollower = new CANTalon(Wiring.SWEEPER_MOTOR_FOLLOWER);
		sweeperFollower.setInverted(true);
		//sweeperFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
		//sweeperFollower.set(Wiring.SWEEPER_MOTOR);
		SmartDashboard.putNumber("Pickup speed: ", .35);
	}

	public void sweeperStart() {
		sweeperMotor.set(SmartDashboard.getNumber("Pickup speed: ",.35));
		sweeperFollower.set(SmartDashboard.getNumber("Pickup speed: ",.35));
		pickingUp = true;
	}
	
	public void sweeperStop() {
		sweeperMotor.set(0);
		sweeperFollower.set(0);
		pickingUp = false;
	}
	
	public void sweeperReverse() {
		sweeperMotor.set(-1);
		sweeperFollower.set(-1);
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
