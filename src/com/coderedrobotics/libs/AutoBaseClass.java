package com.coderedrobotics.libs;

import com.coderedrobotics.DriveAuto;
import com.coderedrobotics.Target;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class AutoBaseClass {
	DriveAuto mDriveAuto;
	Target mTarget;
	int mRobotPosition;
	boolean mIsRunning = false;
	Timer mAutoTimer;

	public AutoBaseClass(DriveAuto driveAuto, Target target, int robotPosition) {
		mDriveAuto = driveAuto;
		mTarget = target;
		mRobotPosition = robotPosition;
		mAutoTimer = new Timer();
	}

	public AutoBaseClass(DriveAuto driveAuto, int robotPosition) {
		this(driveAuto, null, robotPosition);
	}

	public abstract void tick();

	public void start() {
		mAutoTimer.setStage(0);
		mIsRunning = true;
	}

	public void stop() {
		mIsRunning = false;
		mDriveAuto.stop();
	}

	public boolean isRunning() {
		mAutoTimer.tick();  // we need to tick the timer and this is a good place to do it.
		return mIsRunning;
	}

	public int getCurrentStep() {
		return mAutoTimer.getStage();
	}

	public void setStep(int step) {
		mAutoTimer.setStage(step);
	}

	public double getStepTimeRemainingInSeconds() {
		return mAutoTimer.getTimeRemainingSeconds();
	}

	public double getStepTimeRemainingInMilliSeconds() {
		return mAutoTimer.getTimeRemainingMilliseconds();
	}

	public DriveAuto driveAuto() {
		return mDriveAuto;
	}

	public void driveInches(double distance, double maxPower) {
		mDriveAuto.driveInches(distance, maxPower);
	}

	public void turnDegrees(double degrees, double maxPower) {
		mDriveAuto.turnDegrees(degrees, maxPower);
	}

	public int robotPosition() {
		return mRobotPosition;
	}

	public void advanceStep() {
		mAutoTimer.stopTimerAndAdvanceStage();
	}

	// starts a timer for the time indicated and then immediately advances the
	// stage counter
	// this is typically used when starting a driving maneuver because the next
	// stage would
	// be watching to see when the maneuver was completed.
	public void setTimerAndAdvanceStep(long milliseconds) {
		mAutoTimer.setTimerAndAdvanceStage(milliseconds);
	}
	
	public void setTimer(long milliseconds) {
		mAutoTimer.setTimer(milliseconds);
	}
	
	public boolean timeExpired() {
		return mAutoTimer.timeExpired();
	}

}
