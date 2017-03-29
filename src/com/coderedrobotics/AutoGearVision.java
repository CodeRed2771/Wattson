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
				switch (robotPosition()) {
				case 1:
					driveInches(-90, .4);
					break;
				case 2:
					driveInches(-90, .4);
					break;
				case 3:
					driveInches(-104, .4);
					break;
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
					turnDegrees(-52, .4);
					break;
				case 2:
//					if (target.foundTarget()) {
//						turnDegrees(target.getGearAngle(), 1);
//					}
					break;
				case 3:
					turnDegrees(52, .4);
					break;
				}
				break;
				
			case 3:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
				
			case 4:
				setTimerAndAdvanceStep(1000);  // wait a second before checking images
				target.setActiveMode(true); // start saving image data
				break;
				
			case 5:
				break;
				
			case 6:
				if (target.foundTarget()) {
					switch (robotPosition()) {
					case 1:
						turnDegrees(target.getGearAngle(), .5);
						setTimerAndAdvanceStep(2000);
						break;
					case 2:
//						driveInches(target.getGearDistance(), .4);
						setStep(7);
						break;
					case 3:
						turnDegrees(target.getGearAngle(), .5);
						setTimerAndAdvanceStep(2000);
						break;
					}
				}
				break;

			case 7:
				if (driveAuto().hasArrived())
					advanceStep();
				break;

			case 8:
				advanceStep();
				switch (robotPosition()) {
				case 1:
					driveInches(-55, .25);
					break;
				case 2:
					break;
				case 3:
					driveInches(-50, .25);
					break;

				}
			}
		}
	}
}