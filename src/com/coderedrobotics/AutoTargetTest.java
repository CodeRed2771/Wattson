package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoTargetTest extends AutoBaseClass {

	public AutoTargetTest(DriveAuto driveAuto, int robotPosition) {
		super(driveAuto, robotPosition);
	}
	
	public void tick() {
		//SmartDashboard.putBoolean("IsDriving", isDriving());
//		driveAuto.showEncoderValues();
//		
//		//update the pid values based on numbers entered into the SmartDashboard
//		driveAuto.updatePIDValues();
//		target.displayDetails();
//		
//		if (!isDriving && target.foundTarget() && !target.isOnTarget()){
//			driveAuto.turnDegrees(-target.degreesOffTarget(), 1);
//			
//			isDriving = true;
//		}
//		
//		if (!isDriving && target.isOnTarget() && target.foundTarget()) {
//			driveAuto.driveInches(target.distanceFromGearTarget() - 10, .20);
//			isDriving = true;
//		}
//		
//		if (driveAuto.hasArrived()) {
//			isDriving = false;
//		}
	}
}
