package com.coderedrobotics;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Target target;
	Drive drive;
	DriveAuto driveAuto;

	public void robotInit() {
		target = new Target();
		drive = new Drive();
		driveAuto = new DriveAuto(drive);
		driveAuto.stop();
		driveAuto.resetEncoders();
		drive.set(0, 0);
		drive.setPIDstate(true);
	}
	
	public void autonomousInit() {

	}

	@Override
	public void autonomousPeriodic() {

	}

	
	@Override
	public void teleopPeriodic() {
		target.displayDetails();
		if (target.foundTarget()){
			driveAuto.turnDegrees(-target.degreesOffTarget(), .5);
			SmartDashboard.putNumber("degrees off target", target.degreesOffTarget());
		}
	}

	@Override
	public void testPeriodic() {
	}
}
