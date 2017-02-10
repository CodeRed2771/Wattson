package com.coderedrobotics.libs;

import com.coderedrobotics.DriveAuto;
import com.coderedrobotics.Target;

public abstract class AutoBaseClass {
	DriveAuto mDriveAuto;
	Target mTarget;
	int mRobotPosition;
	int mCurrentStep = 0;
	boolean mIsRunning = false;
	Timer mAutoTimer;

	public AutoBaseClass(DriveAuto driveAuto, Target target, int robotPosition) {
		mDriveAuto = driveAuto;
		mTarget = target;
		mRobotPosition = robotPosition;
		mCurrentStep = 0;
		mAutoTimer = new Timer();
		mDriveAuto.stop();
		mDriveAuto.resetEncoders();
	}
	
	public AutoBaseClass(DriveAuto driveAuto, int robotPosition) {
		this(driveAuto, null, robotPosition);
		
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
	
	public DriveAuto driveAuto (){
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
	public void advanceStage() {
		mAutoTimer.stopTimerAndAdvanceStage();
	}
	
	public void setTimerAndAdvanceStage(long milliseconds) {
		mAutoTimer.setTimerAndAdvanceStage(milliseconds);
	}
	
}
