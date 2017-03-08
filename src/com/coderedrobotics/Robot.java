package com.coderedrobotics;
/*
 * 2017 Code Red Robotics
 * 
 * Wattson - Steamworks
 * 
 */
import com.coderedrobotics.libs.AutoBaseClass;
import com.coderedrobotics.libs.Logger;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI.Port;
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
	//ADXRS450_Gyro gyro;
	AnalogGyro gyro;

	SendableChooser autoChooser;
	final String autoPegDVV = "Peg DVV";
	final String autoPegCH = "Peg Caden";
	final String autoDriveForward = "Drive Forward";
	final String autoTurn = "Turn 180";
	final String autoTargetTest = "Target Test";
	final String autoGearEncoderRed = "Gear Encoder Red";
	final String autoTimerTest = "Timer Test";
	String autoSelected;
	
	AutoBaseClass mAutoProgram;
	
	public void robotInit() {
		//gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
		gyro = new AnalogGyro(Wiring.GYRO_PORT);
		//gyro.calibrate();
		
		target = new Target();
		drive = new Drive();
		driveAuto = new DriveAuto(drive, gyro);
//		driveAuto.resetEncoders();
		drive.set(0, 0);
		drive.setPIDstate(true);
		
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
		autoChooser.addObject(autoGearEncoderRed, autoGearEncoderRed);
		autoChooser.addObject(autoTimerTest, autoTimerTest);
		autoChooser.addDefault(autoTargetTest, autoTargetTest);
		SmartDashboard.putData("Auto choices", autoChooser);
		
		SmartDashboard.putNumber("Robot Position", 2);

		gamepad = new KeyMap();

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
		
		//SmartDashboard.putNumber("Gyro", gyro.getAngle());
		
	}

	public void autonomousInit() {
		drive.set(0, 0);
		drive.setPIDstate(true);
		
		driveAuto.reset();
		
		autoSelected = (String) autoChooser.getSelected();
		SmartDashboard.putString("Auto selected: ", autoSelected);
		
		int robotPosition = (int)SmartDashboard.getNumber("Robot Position",2);

		switch (autoSelected) {
		case autoTargetTest:
			mAutoProgram = new AutoTargetTest(driveAuto, robotPosition, target);
			break;
		case autoPegDVV:
			mAutoProgram = new AutoPegDVV(driveAuto, robotPosition);
			break;
		case autoPegCH:
			mAutoProgram = new AutoPegCH(driveAuto, robotPosition);
			break;
		case autoDriveForward:
			mAutoProgram = new AutoDriveForward(driveAuto, robotPosition);
			break;
		case autoTurn:
			mAutoProgram = new AutoTurn(driveAuto, robotPosition);
			break;
		case autoGearEncoderRed:
			mAutoProgram = new AutoGearEncoderRed(driveAuto, robotPosition);
			break;
		case autoTimerTest:
			mAutoProgram = new AutoTimerTest(driveAuto, robotPosition);
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
		target.displayDetails();
	}

	@Override
	public void testPeriodic() {
	}
}
