package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;
import com.coderedrobotics.libs.Timer;

public class AutoPegDVV extends AutoBaseClass {
	
	public AutoPegDVV(DriveAuto driveAuto, int robotPosition) {
		super(driveAuto, robotPosition);
	}

	public void tick() {
		if (isRunning()) {
			switch (getCurrentStep()) {
			case 0:
				break;
			case 1:
				break;
			case 2: 
				break;
			case 3:
				stop();
				break;
			}
		}
	}
	

}
