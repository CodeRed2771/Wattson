package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 TargetTest

 Starts with target in view, aligns itself and drives to target
 
*/
public class AutoTargetTest extends AutoBaseClass {
	Target target;
	boolean foundTarget = false;
	
	public AutoTargetTest(DriveAuto driveAuto, int robotPosition, Target target) {
		super(driveAuto, robotPosition);
		this.target = target;
	}
	
	public void tick() {

		if (isRunning()) {
			switch (getCurrentStep()) {
			case 0:
				// align to the peg target
				target.enableVisionTargetMode(true, "Gear");
				advanceStep();
				//setTimerAndAdvanceStage(2000);
				break;
				
			case 1:
				foundTarget = target.foundTarget();
				if (foundTarget) {
					setTimerAndAdvanceStep(4000);					
					turnDegrees(target.getGearAngle(), .5);
				}
				break;
				
			case 2:
				if (driveAuto().hasArrived()) // done aligning
					setStep(1); // go ahead and do it again
				break;
				
			case 3: 
				setStep(1); // go ahead and do it again
				break;
				
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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
