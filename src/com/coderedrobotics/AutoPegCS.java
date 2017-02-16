package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;

public class AutoPegCS extends AutoBaseClass {

	public AutoPegCS(DriveAuto driveAuto, int robotPosition) {
		super(driveAuto, robotPosition);
	}

	public void tick() {
		if (isRunning()) {
			switch(getCurrentStep()){
			case 0:
				setTimerAndAdvanceStage(3000);
				
			}
		}
	}

}
