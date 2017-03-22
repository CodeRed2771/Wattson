package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
	Auto Boiler Encoder
	
	Starting near the boiler, it drives out, turns, and moves into position at the boiler
	then shoots for a little while, then drives forward
 
*/
public class AutoBoilerEncoder extends AutoBaseClass {
	Target target;
	Shooter shooter;
	GearPickup gearPickup;

	public AutoBoilerEncoder(DriveAuto driveAuto, int robotPosition, Target target, Shooter shooter,  GearPickup gearPickup) {
		super(driveAuto, robotPosition);
		this.target = target;
		this.shooter = shooter;
		this.gearPickup = gearPickup;
	}

	// position 1 = RED ALLIANCE
	// position 2 = BLUE ALLIANCE
	
	public void tick() {
		
		if (isRunning()) {
			switch (getCurrentStep()) {
			case 0:
				setTimerAndAdvanceStep(2000);
				driveInches(-50,.4);
				break;
			case 1:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
			case 2:
				setTimerAndAdvanceStep(1500);
				switch (robotPosition()) {
				case 1:
					turnDegrees(-45,.4);
					break;
				case 2:
					turnDegrees(45, .4);
					break;
				}
				gearPickup.releaseBallPickup();
				break;
			case 3:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
			case 4:
				setTimerAndAdvanceStep(1500);		
				driveInches(44, .4);  // drive a little closer to the boiler
				shooter.spinUpShooter();
				gearPickup.park();
				break;
			case 5:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
			case 6:
				setTimerAndAdvanceStep(7000);   // shoot for 6 seconds and advance stage
				shooter.feedShooter();
				break;
			case 7:
				break;
			case 8:
				setTimerAndAdvanceStep(2000);
				shooter.stopFeeder();
				shooter.stopShooter();
				driveInches(-74, .5);
				break;
			case 9:
				if (driveAuto().hasArrived())
					advanceStep();
				break;
			case 10:
				driveAuto().setPIDstate(false);
				advanceStep();
				break;
			case 11:
				break;
			}
			
		}
	}
}
