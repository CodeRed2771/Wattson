package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 TargetTest

 Starts with target in view, aligns itself and drives to target
 
*/
public class AutoGearEncoder extends AutoBaseClass {

	public AutoGearEncoder(DriveAuto driveAuto, int robotPosition) {
		super(driveAuto, robotPosition);
	}

	public void tick() {
		
		if (isRunning()) {
			switch (getCurrentStep()) {
			case 0:
				setTimerAndAdvanceStep(3000);
				if(robotPosition() == 1 || robotPosition() == 3){
						driveInches(-100,.4);
				} else {
						driveInches(-90, .4);
				}
				break;
			case 1:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
			case 2:
				setTimerAndAdvanceStep(3000);
				switch(robotPosition()){
				case 1:
					turnDegrees(-52,.4);
					break;
				case 2:
					this.setStep(99);
					break;
				case 3:
					turnDegrees(52,.4);
					break;
				}
				break;
			case 3:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
			case 4:
				setTimerAndAdvanceStep(3000);			
				driveInches(-50,.25);
			}
		}
	}
}
