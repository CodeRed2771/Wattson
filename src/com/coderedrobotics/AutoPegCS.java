package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;

public class AutoPegCS extends AutoBaseClass {

	public AutoPegCS(DriveAuto driveAuto, int robotPosition) {
		super(driveAuto, robotPosition);
	}

	public void tick() {
		if (isRunning()) {
			switch (getCurrentStep()) {
			case 0: // position 1
				setTimerAndAdvanceStep(5000);
				if (robotPosition() == 1 || robotPosition() == 3) {
					driveInches(54, .5); // TODO hone in the distance
				}
				if (robotPosition() == 2) {
					driveInches(24, .5);
				}
				break;
			case 1:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
			case 2:
				setTimerAndAdvanceStep(3000);
				if (robotPosition() == 1) {
					turnDegrees(.45, .2); // TODO figure out degrees
				} else if (robotPosition() == 3) {
					turnDegrees(-.45, .2); // opposite of position 1
				} else if (robotPosition() == 2) {
					setStep(6);
				}
				break;
			case 3:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
			case 4:
				setTimerAndAdvanceStep(3000);
				driveInches(20, .3);
				break;
			case 5:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
			case 6:
				setTimerAndAdvanceStep(3000);
				driveInches(-5000, .5);
				break;
			case 7:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
			}
		}
	}

}
