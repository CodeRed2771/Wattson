package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoDriveForward extends AutoBaseClass {

	public AutoDriveForward(DriveAuto driveAuto, int robotPosition) {
		super(driveAuto, robotPosition);
	}

	public void tick() {
		
		if (isRunning()) {
	
			this.driveAuto().showEncoderValues();
			SmartDashboard.putNumber("Auto Step", getCurrentStep());

			switch (getCurrentStep()) {
			case 0:
				setTimerAndAdvanceStep(5000);
				driveInches(-100, .5); 
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
