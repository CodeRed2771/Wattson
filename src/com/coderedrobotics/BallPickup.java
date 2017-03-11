package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;
import com.coderedrobotics.libs.PIDControllerAIAO;
import com.coderedrobotics.libs.PIDSourceFilter;
import com.coderedrobotics.libs.PWMController;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BallPickup {

	private PIDControllerAIAO pid;
	private CANTalon sweeperMotor;
	private CANTalon sweeperFollower;
	private boolean pickingUp = false;
	private boolean parked = false;

	public BallPickup() {

		//SmartDashboard.putNumber("sweep P", 0);
		//SmartDashboard.putNumber("sweep I", 0);
		//SmartDashboard.putNumber("sweep D", 0);

		sweeperMotor = new CANTalon(Wiring.BALL_PICKUP_LEADER);

		sweeperMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		sweeperMotor.configNominalOutputVoltage(0.0f, 0.0f);
		sweeperMotor.configPeakOutputVoltage(12, 12);
		sweeperMotor.setInverted(true);
		sweeperMotor.changeControlMode(TalonControlMode.PercentVbus);

		sweeperFollower = new CANTalon(Wiring.BALL_PICKUP_FOLLOWER);

		sweeperFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
		sweeperFollower.reverseOutput(true);
		sweeperFollower.set(sweeperMotor.getDeviceID());


		pid = new PIDControllerAIAO(Calibration.BALL_PICKUP_P, Calibration.BALL_PICKUP_I, Calibration.BALL_PICKUP_D,
				new PIDSourceFilter((value) -> -sweeperMotor.getEncPosition()), (output) -> sweeperMotor.set(output), false, "");
	}

	private void sweeperStart() {
		if (!parked) {
		sweeperMotor.set(SmartDashboard.getNumber("Pickup speed: ",.35));
		pickingUp = true;
		}
	}

	private void sweeperReverse() {
		if (!parked) {
		sweeperMotor.set(-1);
		}
	}

	public void togglePickup() {
		if (pickingUp) {
			setPark(true);
		} else {
			setPark(false);
			sweeperStart();
		}

	}

	public void setPark(boolean park) {
		if (park) {
			pickingUp = false;
			pid.enable();
			pid.setSetpoint(((-sweeperMotor.getEncPosition()/Calibration.BALL_PICKUP_TICKS_PER_REV)*Calibration.BALL_PICKUP_TICKS_PER_REV) + (Calibration.BALL_PICKUP_TICKS_PER_REV * Calibration.BALL_PICKUP_PARK_POSITION));
			parked = true;
		} else {
			pid.disable();
			parked = false;
		}
	}

	public void tick() {
		SmartDashboard.putNumber("sweeper encoder", sweeperMotor.getEncPosition());

//		pid.setPID(
//				SmartDashboard.getNumber("sweep P", 0),
//				SmartDashboard.getNumber("sweep I", 0),
//				SmartDashboard.getNumber("sweep D", 0));
	}
}
