package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 TargetTest

 Starts with target in view, aligns itself and drives to target
 
*/
public class AutoBoiler extends AutoBaseClass {
	Target target;
	Shooter shooter;
	boolean foundTarget = false;

	public AutoBoiler(DriveAuto driveAuto, int robotPosition, Target target, Shooter shooter) {
		super(driveAuto, robotPosition);
		this.target = target;
		this.shooter = shooter;
	}

	public void tick() {

		if (isRunning()) {
			switch (getCurrentStep()) {
			case 0:
				// align to the peg target
				target.enableVisionTargetMode(true, "Boiler");
				// setTimerAndAdvanceStage(2000);
				break;

			case 1:
				foundTarget = target.foundBoilerTarget();
				if (foundTarget) {
					advanceStep();
				} else {
					setStep(1000);
				}
				break;

			case 2:
				if (foundTarget) {
					setTimerAndAdvanceStep(2000);
					turnDegrees(target.getBoilerAngle(), 1);
				} else {
					// we just sit here because we can't see the target
				}
				break;

			case 3:
				if (driveAuto().hasArrived()) // done aligning
					advanceStep();
				break;

			case 4:
				// drive toward peg target and stop 10" short
				setTimerAndAdvanceStep(5000);
				driveInches(80, .20);// 80 in is about the half way from gear
										// target to boiler
				shooter.spinUpShooter();
				break;

			case 5:
				if (driveAuto().hasArrived())
					advanceStep();
				break;

			case 6:
				shooter.feedShooter();
				target.enableVisionTargetMode(false, "");
				setTimerAndAdvanceStep(5000);
				break;
				
			case 7:
				break;
				
			case 8:
				shooter.stopShooter();
				advanceStep();
				break;
				
			case 9:
				target.enableVisionTargetMode(false, "");
				break;
			}
		}

		// SmartDashboard.putBoolean("IsDriving", isDriving());
		// driveAuto.showEncoderValues();
		//
		// //update the pid values based on numbers entered into the
		// SmartDashboard
		// driveAuto.updatePIDValues();
		// target.displayDetails();
		//
		// if (!isDriving && target.foundTarget() && !target.isOnTarget()){
		// driveAuto.turnDegrees(-target.degreesOffTarget(), 1);
		//
		// isDriving = true;
		// }
		//
		// if (!isDriving && target.isOnTarget() && target.foundTarget()) {
		// driveAuto.driveInches(target.distanceFromGearTarget() - 10, .20);
		// isDriving = true;
		// }
		//
		// if (driveAuto.hasArrived()) {
		// isDriving = false;
		// }
	}
}
