package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class AutoTimerTest extends AutoBaseClass {

	public AutoTimerTest(DriveAuto driveAuto, int robotPosition) {
		super(driveAuto, robotPosition);
	}

	public void tick() {
		SmartDashboard.putNumber("DVV STEP", getCurrentStep());
		SmartDashboard.putBoolean("DVV RUNNING", isRunning());
		
		if (isRunning()) {
			switch (getCurrentStep()) {
			case 0:
				setTimerAndAdvanceStage(3000);
				break;
			case 1:
				break;
			case 2:
				setTimerAndAdvanceStage(3000);
				break;
			case 3:
				break;
			case 4:
				setTimerAndAdvanceStage(3000);
				break;
			}
		}
	}
}
