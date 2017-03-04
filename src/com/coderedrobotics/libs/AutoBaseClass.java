package com.coderedrobotics.libs;

import com.coderedrobotics.DriveAuto;
import com.coderedrobotics.Target;

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
//		mDriveAuto.stop();
//		mDriveAuto.resetEncoders();

	}

	public AutoBaseClass(DriveAuto driveAuto, int robotPosition) {
		this(driveAuto, null, robotPosition);

	}

	public abstract void tick();

	public void start() {
		mAutoTimer.setStage(0);
//		mDriveAuto.resetEncoders();
		mIsRunning = true;
	}

	public void stop() {
		mIsRunning = false;
		mDriveAuto.stop();
	}

	public boolean isRunning() {
		return mIsRunning;
	}

	public int getCurrentStep() {
		return mAutoTimer.getStage();
	}

	public void setCurrentStep(int step) {
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

	public double degreesOffTarget() {
		return mTarget.degreesOffTarget();
	}

	public double distanceFromTarget() {
		return mTarget.distanceFromGearTarget();
	}

	public int robotPosition() {
		return mRobotPosition;
	}

	public void advanceStage() {
		mAutoTimer.stopTimerAndAdvanceStage();
	}

	// starts a timer for the time indicated and then immediately advances the
	// stage counter
	// this is typically used when starting a driving maneuver because the next
	// stage would
	// be watching to see when the maneuver was completed.
	public void setTimerAndAdvanceStage(long milliseconds) {
		mAutoTimer.setTimerAndAdvanceStage(milliseconds);
	}

}
