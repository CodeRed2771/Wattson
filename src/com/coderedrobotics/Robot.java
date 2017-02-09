package com.coderedrobotics;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Target target;
	Drive drive;
	DriveAuto driveAuto;
	boolean isDriving = false;
	
	public void robotInit() {
		target = new Target();
		drive = new Drive();
		driveAuto = new DriveAuto(drive);
		driveAuto.stop();
		driveAuto.resetEncoders();
		drive.set(0, 0);
		drive.setPIDstate(true);
		
		target.displayDetails();
		
		driveAuto.showPIDValues();
		drive.disablePID();
	}
	
	public void autonomousInit() {
		//driveAuto.driveInches(20, .3);
	}

	@Override
	public void autonomousPeriodic() {


		SmartDashboard.putBoolean("IsDriving", isDriving);
		driveAuto.showEncoderValues();
		
		//update the pid values based on numbers entered into the SmartDashboard
		driveAuto.updatePIDValues();
		target.displayDetails();
		
		if (!isDriving && target.foundTarget() && !target.isOnTarget()){
			driveAuto.turnDegrees(-target.degreesOffTarget(), 1);
			
			isDriving = true;
		}
		
		if (!isDriving && target.isOnTarget() && target.foundTarget()) {
			driveAuto.driveInches(target.distanceFromGearTarget() - 10, .20);
			isDriving = true;
		}
		
		if (driveAuto.hasArrived()) {
			isDriving = false;
		}
	}

	@Override
	public void teleopInit() {
		driveAuto.resetEncoders();
		drive.set(0, 0);
	}
	
	@Override
	public void teleopPeriodic() {
	
	}

	@Override
	public void testPeriodic() {
	}
}
