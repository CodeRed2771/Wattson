package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;
import com.coderedrobotics.libs.PWMController;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallPickup {

	private CANTalon sweeperMotor;
	private CANTalon sweeperFollower;
	private boolean pickingUp = false;
	private boolean parked = false;
	
	public BallPickup() {
		
		sweeperMotor = new CANTalon(Wiring.SWEEPER_MOTOR);
		
		sweeperMotor.setPID(Calibration.SHOOTER_P, Calibration.SHOOTER_I, Calibration.SHOOTER_D);
		sweeperMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		sweeperMotor.configNominalOutputVoltage(0.0f, 0.0f);
		sweeperMotor.configPeakOutputVoltage(12, 12);
		sweeperMotor.setProfile(0);
		sweeperMotor.setPID(Calibration.SHOOTER_P, Calibration.SHOOTER_I, Calibration.SHOOTER_D);
		sweeperMotor.changeControlMode(TalonControlMode.PercentVbus);
		
		sweeperFollower = new CANTalon(Wiring.SWEEPER_MOTOR_FOLLOWER);
		
		sweeperFollower.setInverted(true);
		sweeperFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
		sweeperFollower.set(Wiring.SWEEPER_MOTOR);
		
		SmartDashboard.putNumber("Pickup speed: ", .35);
	}

	public void sweeperStart() {
		if (!parked) {
		sweeperMotor.set(SmartDashboard.getNumber("Pickup speed: ",.35));
		pickingUp = true;
		}
	}
	
	public void sweeperStop() {
		if (!parked) {
		sweeperMotor.set(0);
		pickingUp = false;
		}
	}
	
	public void sweeperReverse() {
		if (!parked) {
		sweeperMotor.set(-1);
		}
	}

	public void togglePickup() {
		if (pickingUp) {
			sweeperStop();
		} else
			sweeperStart();
	}
	
	public void holdParkPosition(boolean putInPark ) {
		if (putInPark) {
			sweeperStop();
			sweeperMotor.changeControlMode(TalonControlMode.Position);

			sweeperMotor.set(Calibration.PICKUP_PARK_POSITION);
			parked = true;
			SmartDashboard.putNumber("Park Position", Calibration.PICKUP_PARK_POSITION);
			//sweeperMotor.set(Calibration.PICKUP_PARK_POSITION); // set absolute position
		} else {
			sweeperMotor.changeControlMode(TalonControlMode.PercentVbus);
			parked = false;
		}
	}

	public void tick() {
		if (parked) {
			sweeperMotor.set(SmartDashboard.getNumber("Park Position", Calibration.PICKUP_PARK_POSITION));
		}
	}
}
