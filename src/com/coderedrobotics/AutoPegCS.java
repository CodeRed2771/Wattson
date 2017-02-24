package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;

public class AutoPegCS extends AutoBaseClass {

	public AutoPegCS(DriveAuto driveAuto, int robotPosition) {
		super(driveAuto, robotPosition);
	}

	public void tick() {
		if (isRunning()) {
			switch (getCurrentStep()) {
			case 0: //position 1
				setTimerAndAdvanceStage(5000);
				driveInches(54, .5); //TODO hone in the distance
				break;
			case 1: 
				if(driveAuto().hasArrived())
					advanceStage();
					break;
			case 2:
				setTimerAndAdvanceStage(3000);
					turnDegrees(.45, .2);
					break;
			case 3:
				if(driveAuto().hasArrived())
					advanceStage();
					break;
			case 4:
				setTimerAndAdvanceStage(3000);
				driveInches(20, .3);
				break;
			case 5:
				if(driveAuto().hasArrived())
					advanceStage();
				break;
			case 6:
				setTimerAndAdvanceStage(3000);
				driveInches(-5000, .5);
				break;
			case 7:
				if(driveAuto().hasArrived())
					advanceStage();
					break;
			}
		}
	}

}
