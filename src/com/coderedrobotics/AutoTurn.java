package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoTurn extends AutoBaseClass {

	public AutoTurn(DriveAuto driveAuto, int robotPosition) {
		super(driveAuto, robotPosition);
	}

	public void tick() {
		
		if (isRunning()) {
			this.driveAuto().showEncoderValues();
			
			switch (getCurrentStep()) {
			case 0:
				setTimerAndAdvanceStage(5000);
				turnDegrees(180, .6);
				break;
			case 1:
				if (driveAuto().hasArrived())
					advanceStage();
				break;
			case 2:
				break;
			}
		}

	}

}
