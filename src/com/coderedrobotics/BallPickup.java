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
		
		sweeperMotor = new CANTalon(Wiring.BALL_PICKUP_LEADER);
		
		sweeperMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		sweeperMotor.configNominalOutputVoltage(0.0f, 0.0f);
		sweeperMotor.configPeakOutputVoltage(12, 12);
		sweeperMotor.setProfile(0);
		sweeperMotor.setPID(.1, 0, 0);
		sweeperMotor.setInverted(true);
		sweeperMotor.changeControlMode(TalonControlMode.PercentVbus);
		
		sweeperFollower = new CANTalon(Wiring.BALL_PICKUP_FOLLOWER);
		
		sweeperFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
		sweeperFollower.reverseOutput(true);
		sweeperFollower.set(sweeperMotor.getDeviceID());
		
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

			sweeperMotor.set(.5);
			parked = true;
			SmartDashboard.putNumber("Park Position", .5);
			//sweeperMotor.set(Calibration.PICKUP_PARK_POSITION); // set absolute position
		} else {
			sweeperMotor.changeControlMode(TalonControlMode.PercentVbus);
			parked = false;
		}
	}

	public void tick() {
		SmartDashboard.putNumber("Pickup Position", sweeperMotor.getPosition());

		if (parked) {
			sweeperMotor.set(SmartDashboard.getNumber("Park Position", .5));
		}
	}
}
