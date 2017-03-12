package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
					driveInches(-86, .4);
				} else {
					driveInches(-90, .4);
				}
				break;
				
			case 1:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
				
			case 2:
				setTimerAndAdvanceStep(2000);
				switch (robotPosition()) {
				case 1:
					turnDegrees(-55, .4);
					break;
				case 2:
//					if (target.foundTarget()) {
//						turnDegrees(target.getGearAngle(), 1);
//					}
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
				setTimerAndAdvanceStep(2000);
				break;
				
			case 5:
				if (target.foundTarget()) {
					switch (robotPosition()) {
					case 1:
						turnDegrees(target.getGearAngle(), 1);
						setTimerAndAdvanceStep(2000);
						break;
					case 2:
//						driveInches(target.getGearDistance(), .4);
						setStep(7);
						break;
					case 3:
						turnDegrees(target.getGearAngle(), 1);
						setTimerAndAdvanceStep(2000);
						break;
					}
				}
				break;

			case 6:
				if (driveAuto().hasArrived())
					advanceStep();
				break;

			case 7:
				advanceStep();
				switch (robotPosition()) {
				case 1:
					driveInches(-50, .4);
					break;
				case 2:
					break;
				case 3:
					driveInches(-50, .4);
					break;

				}
			}
		}
	}
}