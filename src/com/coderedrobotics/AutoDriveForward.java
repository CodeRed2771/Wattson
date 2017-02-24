package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;

public class AutoDriveForward extends AutoBaseClass {

	public AutoDriveForward(DriveAuto driveAuto, int robotPosition) {
		super(driveAuto, robotPosition);
	}

	public void tick() {
		if (isRunning()) {
			switch (getCurrentStep()) {
			case 0:
				setTimerAndAdvanceStage(5000);
				driveInches(33, .5);
				break;
			case 1:
				if (driveAuto().hasArrived())
					advanceStage();
				break;
			case 2:
				stop();
				break;
			}
		}

	}

}
