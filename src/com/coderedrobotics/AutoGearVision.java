package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 TargetTest

 Starts with target in view, aligns itself and drives to target
 
*/
public class AutoGearVision extends AutoBaseClass {

	private Target target;
	boolean foundTarget = false;

	public AutoGearVision(DriveAuto driveAuto, int robotPosition, Target target) {
		super(driveAuto, robotPosition);
		this.target = target;
	}

	public void tick() {

		if (isRunning()) {
			switch (getCurrentStep()) {
			case 0:
				target.enableVisionTargetMode(true, "Gear");
				setTimerAndAdvanceStep(4000);
				if (robotPosition() == 1 || robotPosition() == 3) {
					driveInches(-76, .4);
				} else {
					driveInches(-60, .4);
				}
				break;
				
			case 1:
				//foundTarget = target.foundTarget();
				if (driveAuto().hasArrived())
					advanceStep();
				break;
				
			case 2:
				setTimerAndAdvanceStep(3000);
				switch (robotPosition()) {
				case 1:
					turnDegrees(-55, .4);
					break;
				case 2:
					if (target.foundTarget()) {
						turnDegrees(target.getGearAngle(), 1);
					}
					break;
				case 3:
					turnDegrees(55, .4);
					break;
				}
				break;
				
			case 3:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
				
			case 4:
				setTimerAndAdvanceStep(3000);
				if (target.foundTarget()) {
					switch (robotPosition()) {
					case 1:
						turnDegrees(target.getGearAngle(), 1);
						break;
					case 2:
						driveInches(target.getGearDistance(), .4);
						break;
					case 3:
						turnDegrees(target.getGearAngle(), 1);
						break;
					}
				}
				break;

			case 5:
				if (driveAuto().hasArrived())
					advanceStep();
				break;

			case 6:
				advanceStep();
				switch (robotPosition()) {
				case 1:
					driveInches(target.getGearDistance(), .4);
					break;
				case 2:
					break;
				case 3:
					driveInches(target.getGearDistance(), .4);
					break;

				}
			}
		}
	}
}