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
	private boolean enabled = false;

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

		pid.setOutputRange(-0.4, 0.4);
	}

	public void toggle() {
		set(!enabled);
	}

	public void park() {
		if (enabled) set(false);
	}

	public void start() {
		if (!enabled) set(true);
	}

	public void set(boolean enable) {
		if (enabled = enable) {
			pid.disable();
			sweeperMotor.set(Calibration.BALL_PICKUP_SPEED);
		} else {
			pid.enable();
			pid.setSetpoint(((-sweeperMotor.getEncPosition()/Calibration.BALL_PICKUP_TICKS_PER_REV)*Calibration.BALL_PICKUP_TICKS_PER_REV) + (Calibration.BALL_PICKUP_TICKS_PER_REV * Calibration.BALL_PICKUP_PARK_POSITION));
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
