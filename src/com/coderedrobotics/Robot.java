package com.coderedrobotics;

import com.coderedrobotics.libs.HID.HID;
import com.coderedrobotics.libs.HID.HID.ButtonState;
import com.coderedrobotics.libs.HID.LogitechF310;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Target target;
	Drive drive;
	DriveAuto driveAuto;
	boolean isDriving = false;
	
	LogitechF310 gamepad;
	ButtonState state;
	
	
	public void robotInit() {
		target = new Target();
		drive = new Drive();
		driveAuto = new DriveAuto(drive);
		driveAuto.stop();
		driveAuto.resetEncoders();
		drive.set(0, 0);
		drive.setPIDstate(true);
		
		target.displayDetails();
		
		gamepad = new LogitechF310(0);
		state = HID.newButtonState();
		
		driveAuto.showPIDValues();
		drive.disablePID();
	}
	
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {

	}

	@Override
	public void teleopInit() {
		driveAuto.resetEncoders();
		drive.set(0, 0);
	}
	
	@Override
	public void teleopPeriodic() {
		
		//update the pid values based on numbers entered into the SmartDashboard
		driveAuto.updatePIDValues();
		
		if (target.foundTarget() && !target.isOnTarget()){
			driveAuto.turnDegrees(-target.degreesOffTarget(), 1);
			SmartDashboard.putNumber("degrees off target", target.degreesOffTarget());
			isDriving = true;
		}
		
		if (!isDriving && target.isOnTarget() && target.foundTarget()) {
			driveAuto.driveInches(target.distanceFromGearTarget() - 10, .20);
			isDriving = true;
		}
		
		if (driveAuto.hasArrived()) {
			isDriving = false;
		}
		if (gamepad.buttonPressed(LogitechF310.A, state))
		drive.setRobotPosbyC(23, 52, 30);
		drive.step();
		
	}

	@Override
	public void testPeriodic() {
	}
}
