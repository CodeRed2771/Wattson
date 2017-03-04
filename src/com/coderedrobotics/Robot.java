package com.coderedrobotics;
/*
 * 2017 Code Red Robotics
 * 
 * Wattson - Steamworks
 * 
 */
import com.coderedrobotics.libs.AutoBaseClass;
import com.coderedrobotics.libs.Logger;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Target target;
	Drive drive;
	DriveAuto driveAuto;
	KeyMap gamepad;
	Shooter shooter;
	Climber climber;
	BallPickup ballPickup;
	GearPickup gearPickup;
	AnalogGyro gyro;

	SendableChooser autoChooser;
	final String autoPegDVV = "Peg DVV";
	final String autoPegCH = "Peg Caden";
	final String autoDriveForward = "Drive Forward";
	final String autoTurn = "Turn 180";
	final String autoTargetTest = "Target Test";
	String autoSelected;
	
	AutoBaseClass mAutoProgram;
	
	public void robotInit() {
		gyro = new AnalogGyro(Wiring.GYRO_PORT);
		gyro.initGyro();
		
		target = new Target();
		drive = new Drive();
		driveAuto = new DriveAuto(drive, gyro);
//		driveAuto.resetEncoders();
		drive.set(0, 0);
		drive.setPIDstate(true);

		target.displayDetails();
		
		shooter = new Shooter(target);
		climber = new Climber();
		ballPickup = new BallPickup();
		gearPickup = new GearPickup();
				
//		driveAuto.showPIDValues();
		//drive.disablePID();

		autoChooser = new SendableChooser();
		autoChooser.addObject(autoPegDVV, autoPegDVV);
		autoChooser.addObject(autoPegCH, autoPegCH);
		autoChooser.addObject(autoDriveForward, autoDriveForward);
		autoChooser.addObject(autoTurn, autoTurn);
		autoChooser.addDefault(autoTargetTest, autoTargetTest);
		SmartDashboard.putData("Auto choices", autoChooser);

		gamepad = new KeyMap();
		
		gyro.calibrate();
	}

	public void teleopInit() {
		// driveAuto.resetEncoders();
		drive.set(0, 0);
		target.enableVisionTargetMode(false);
		gearPickup.park();
		
		// ballPickup.holdParkPosition(true);  // FOR TESTING ONLY
	}

	@Override
	public void teleopPeriodic() {

		// Drive
		if (gamepad.flipDrive()){
			drive.set(-gamepad.getRightAxis(), -gamepad.getLeftAxis());			
		} else {
			drive.set(gamepad.getLeftAxis(), gamepad.getRightAxis());			
		}
		
		// Shooter
		if(gamepad.shooterWheels()){
			shooter.toggleShooter();
		}
		
		// Start/stop shooter intake (BOTTOM) - only shoot while holding the button
		if(gamepad.shooterIntake()){
			shooter.feedShooter();
		} else
			shooter.stopFeeder();

		// Start Pickup
		if (gamepad.pickup()) {
			ballPickup.togglePickup();
		}
		
		// camera views
		if (gamepad.cameraGearPickupView()) {
			target.gearPickupView();
		}
		
		if (gamepad.cameraRegularView()) {
			target.regularView();
		}
		
		// Gear
		if(gamepad.gearArm()) {
			gearPickup.toggleArm();
		}
		if(gamepad.retractGearArm()){
			gearPickup.park();
		}
		if (gamepad.gearRelease()) {
			gearPickup.releaseGear();
		}
		
		// climber
		climber.climb(gamepad.getClimberAxis());

		shooter.tick();
		ballPickup.tick();
		gearPickup.tick();
		
	}

	public void autonomousInit() {
		drive.set(0, 0);
		drive.setPIDstate(true);
		
		driveAuto.reset();
		
		autoSelected = (String) autoChooser.getSelected();
		SmartDashboard.putString("Auto selected: ", autoSelected);

		switch (autoSelected) {
		case autoTargetTest:
			mAutoProgram = new AutoTargetTest(driveAuto, 1, target);
			break;
		case autoPegDVV:
			mAutoProgram = new AutoPegDVV(driveAuto, 1);
			break;
		case autoPegCH:
			mAutoProgram = new AutoPegCH(driveAuto, 1);
			break;
		case autoDriveForward:
			mAutoProgram = new AutoDriveForward(driveAuto, 1);
			break;
		case autoTurn:
			mAutoProgram = new AutoTurn(driveAuto, 1);
			break;
		default:
			Logger.getInstance().log("UNKNOWN AUTO SELECTED");
			// SHOULD PICK ONE HERE
			break;
		}

		mAutoProgram.start();

		Logger.getInstance().log("start auto");
	}

	public void autonomousPeriodic() {
		mAutoProgram.tick();
		driveAuto.tick();
		driveAuto.showEncoderValues();
	}

	@Override
	public void testPeriodic() {
	}
}
