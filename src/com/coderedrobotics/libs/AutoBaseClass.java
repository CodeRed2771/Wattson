package com.coderedrobotics.libs;

import com.coderedrobotics.DriveAuto;

public abstract class AutoBaseClass {
	DriveAuto mDriveAuto;
	int mRobotPosition;
	int mCurrentStep = 0;
	boolean mIsRunning = false;
	Timer mAutoTimer;

	public AutoBaseClass(DriveAuto driveAuto, int robotPosition) {
		mDriveAuto = driveAuto;
		mRobotPosition = robotPosition;
		mCurrentStep = 0;
		mAutoTimer = new Timer();
		mDriveAuto.stop();
		mDriveAuto.resetEncoders();
	}
	
	public abstract void tick();
	
	public void start() {
		mAutoTimer.setStage(0);
		mDriveAuto.resetEncoders();
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
		return mCurrentStep;
	}
	
	public double getStepTimeRemainingInSeconds() {
		return mAutoTimer.getTimeRemainingSeconds();
	}
	
	public double getStepTimeRemainingInMilliSeconds() {
		return mAutoTimer.getTimeRemainingMilliseconds();
	}
}
