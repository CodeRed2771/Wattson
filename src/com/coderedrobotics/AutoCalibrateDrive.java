package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoCalibrateDrive extends AutoBaseClass {

	public AutoCalibrateDrive(DriveAuto driveAuto, int robotPosition) {
		super(driveAuto, robotPosition);
	}

	public void tick() {
		
		if (isRunning()) {
	
			this.driveAuto().showEncoderValues();
			SmartDashboard.putNumber("Auto Step", getCurrentStep());

			switch (getCurrentStep()) {
			case 0:
				setTimerAndAdvanceStep(5000);
				driveInches(40, .6);
				break;
			case 1:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
			case 2:
				//stop();
				break;
			}
		}

	}

}
