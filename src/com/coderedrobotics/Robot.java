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
import edu.wpi.first.wpilibj.PowerDistributionPanel;
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
	PowerDistributionPanel pdp;
	double[][] history = new double[16][50];
	int hIndex = 0;

	SendableChooser autoChooser;
	final String autoDriveForward = "Drive Forward";
	final String autoCalibrateDrive = "Calibrate Drive";
	final String autoCalibrateTurn = "Calibrate Turn 180";
	final String autoTargetTest = "Target Test";
	final String autoGearEncoder = "Gear Encoder";
	final String autoGearVision = "autoGearVision";
	final String autoTimerTest = "Timer Test";
	String autoSelected;
	
	AutoBaseClass mAutoProgram;
	
	public void robotInit() {
		
		pdp = new PowerDistributionPanel();
		
		//gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
		gyro = new AnalogGyro(Wiring.GYRO_PORT);
		gyro.calibrate();
		
		target = new Target();
		drive = new Drive();
		driveAuto = new DriveAuto(drive, gyro);

		drive.set(0, 0);
		drive.setPIDstate(true);
		
		shooter = new Shooter(target);
		climber = new Climber();
		ballPickup = new BallPickup();
		gearPickup = new GearPickup();
				
//		driveAuto.showPIDValues();

		autoChooser = new SendableChooser();

		autoChooser.addDefault(autoDriveForward, autoDriveForward);
		autoChooser.addObject(autoGearEncoder, autoGearEncoder);
		autoChooser.addObject(autoGearVision, autoGearVision);
		autoChooser.addObject(autoTargetTest, autoTargetTest);
		autoChooser.addObject(autoCalibrateTurn, autoCalibrateTurn);
		autoChooser.addObject(autoCalibrateDrive, autoCalibrateDrive);

		SmartDashboard.putData("Auto choices", autoChooser);
		SmartDashboard.putNumber("Robot Position", 2);

		gamepad = new KeyMap();

		target.setTargetingExposure(true);
	}

	public void teleopInit() {
		driveAuto.setPIDstate(false);
		drive.setPIDstate(true);
		drive.set(0, 0);
		target.enableVisionTargetMode(false, "");
		gearPickup.park();
		
		// ballPickup.setPark(true);  // FOR TESTING ONLY
	}

	@Override
	public void teleopPeriodic() {

		//double l1 = pdp.getCurrent(Wiring.LEFT_DRIVE_MOTOR1);
		//double l2 = pdp.getCurrent(Wiring.LEFT_DRIVE_MOTOR2);
		//double r1 = pdp.getCurrent(Wiring.RIGHT_DRIVE_MOTOR1);
		//double r2 = pdp.getCurrent(Wiring.RIGHT_DRIVE_MOTOR2);

		hIndex = (hIndex + 1) % 50;
		for (int i = 0; i < 16; i++) {
			double current = pdp.getCurrent(i);
			SmartDashboard.putNumber("PDP"+i, current);
			history[i][hIndex] = current;
			double max = 0;
			double total = 0;
			for (int j = 0; j < 50; j++) {
				max = Math.max(max, history[i][j]);
				total += history[i][j];
			}
			SmartDashboard.putNumber("Peak"+i, max);
			SmartDashboard.putNumber("Avg"+i, total/50);
			
		}
		
		//SmartDashboard.putNumber("Drive Total", l1 + l2 + r1 + r2);

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
			gearPickup.giveTheKrakenATurn();
			ballPickup.toggle();
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
			ballPickup.park();
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
		
		drive.tick();
		SmartDashboard.putNumber("Gyro", gyro.getAngle());
		
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
		case autoCalibrateTurn:
			mAutoProgram = new AutoCalibrateTurn(driveAuto, robotPosition);
			break;
		case autoGearVision:
			mAutoProgram = new AutoGearVision(driveAuto, robotPosition, target);
			break;
		case autoDriveForward:
			mAutoProgram = new AutoDriveForward(driveAuto, robotPosition);
			break;
		case autoCalibrateDrive:
			mAutoProgram = new AutoCalibrateDrive(driveAuto, robotPosition);
			break;
		case autoGearEncoder:
			mAutoProgram = new AutoGearEncoder(driveAuto, robotPosition);
			break;
		case autoTimerTest:
			mAutoProgram = new AutoTimerTest(driveAuto, robotPosition);
			break;
		default:
			Logger.getInstance().log("UNKNOWN AUTO SELECTED");
			// Do the drive forward
			mAutoProgram = new AutoDriveForward(driveAuto, robotPosition);
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
