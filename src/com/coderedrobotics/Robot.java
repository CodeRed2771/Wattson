package com.coderedrobotics;

/*
 * 2017 Code Red Robotics
 * 
 * Wattson - Steamworks
 * 
 */
import com.coderedrobotics.libs.AutoBaseClass;
import com.coderedrobotics.libs.Logger;
import com.coderedrobotics.libs.Timer;

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
	ADXRS450_Gyro gyro;
	//AnalogGyro gyro;
	Timer autoDriveTimer;
	double lastLeftStick = 0;
	double lastRightStick = 0;
	
	SendableChooser autoChooser;
	final String autoDriveForward = "Drive Forward";
	final String autoCalibrateDrive = "Calibrate Drive";
	final String autoCalibrateTurn = "Calibrate Turn 180";
	final String autoTargetTest = "Target Test";
	final String autoGearEncoder = "Gear Encoder"; // use the vision version instead
	final String autoGearVision = "Gear Vision";
	final String autoTimerTest = "Timer Test";
	final String autoBoilerEncoder = "Boiler Encoder";
	String autoSelected;

	AutoBaseClass mAutoProgram;

	boolean autoDriving = false;

	public void robotInit() {
		gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
		//gyro = new AnalogGyro(Wiring.GYRO_PORT);
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

		autoDriveTimer = new Timer();

		// driveAuto.showPIDValues();

		autoChooser = new SendableChooser();

		autoChooser.addDefault(autoDriveForward, autoDriveForward);
		//autoChooser.addObject(autoGearEncoder, autoGearEncoder);
		autoChooser.addObject(autoGearVision, autoGearVision);
		autoChooser.addObject(autoBoilerEncoder, autoBoilerEncoder);
		autoChooser.addObject(autoTargetTest, autoTargetTest);
		autoChooser.addObject(autoCalibrateTurn, autoCalibrateTurn);
		autoChooser.addObject(autoCalibrateDrive, autoCalibrateDrive);

		SmartDashboard.putData("Auto choices", autoChooser);
		SmartDashboard.putNumber("Robot Position", 2);
		
		SmartDashboard.putNumber("Ramp Rate", .08);

		gamepad = new KeyMap();

		target.setTargetingExposure(true);
	}

	public void teleopInit() {
		driveAuto.setPIDstate(false);
		drive.setPIDstate(true);
		drive.set(0, 0);
		target.enableVisionTargetMode(false, "");
		gearPickup.park();

		// ballPickup.setPark(true); // FOR TESTING ONLY
	}

	
	@Override
	public void teleopPeriodic() {
		// Drive
		if (!autoDriving) {
			if (gamepad.flipDrive()) {
				lastLeftStick = RampChange(-gamepad.getLeftAxis(), lastLeftStick);
				lastRightStick = RampChange(-gamepad.getRightAxis(), lastRightStick);
				drive.set(lastRightStick, lastLeftStick);
			} else {
				lastLeftStick = RampChange(gamepad.getLeftAxis(), lastLeftStick);
				lastRightStick = RampChange(gamepad.getRightAxis(), lastRightStick);
				drive.set(lastLeftStick, lastRightStick);
			}
		}

		// Shooter
		if (gamepad.shooterWheels()) {
			shooter.toggleShooter();
		}

		// Start/stop shooter intake (BOTTOM) - only shoot while holding the
		// button
		if (gamepad.shooterIntake()) {
			shooter.feedShooter();
		} else
			shooter.stopFeeder();

		// Turn on/off ball pickup
		if (gamepad.pickup()) {
			gearPickup.giveTheKrakenATurn();
			ballPickup.toggle();
		}

		// camera views
		if (gamepad.cameraGearPickupView()) {
			target.gearPickupView();
		}

		if (gamepad.cameraRegularView()) {
			target.gearReceiverView();
		}

		// Gear
		if (gamepad.gearArm()) {
			ballPickup.park(); // any time we're doing something with Gear Arm,
								// make sure pickup is parked
			gearPickup.toggleArm();
		}

		if (gamepad.retractGearArm()) {
			gearPickup.park();
			ballPickup.park(); // any time we're doing something with Gear Arm,
								// make sure pickup is parked
		}

		if (gamepad.gearRelease()) {
			gearPickup.releaseGear();
			ballPickup.park(); // any time we're doing something with Gear Arm,
								// make sure pickup is parked
		}

		if (gamepad.gearAutoPosition() && !autoDriving) {
			autoDriving = true;
			driveAuto.reset();
			driveAuto.setPIDstate(true);
			driveAuto.driveInches(9, .7);
			autoDriveTimer.setTimer(1000);
		}

		if (autoDriving) {
			driveAuto.tick();
			if (autoDriveTimer.timeExpired()) {
				autoDriving = false;
				driveAuto.setPIDstate(false);
			}
		}

		// climber
		climber.climb(gamepad.getClimberAxis());

		shooter.tick();
		ballPickup.tick();
		gearPickup.tick();

		drive.tick();

		SmartDashboard.putNumber("Gyro", gyro.getAngle());
		target.displayDetails();

	}

	public void autonomousInit() {
		drive.set(0, 0);
		drive.setPIDstate(true);

		driveAuto.reset();

		autoSelected = (String) autoChooser.getSelected();
		SmartDashboard.putString("Auto selected: ", autoSelected);

		int robotPosition = (int) SmartDashboard.getNumber("Robot Position", 2);

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
		case autoBoilerEncoder:
			mAutoProgram = new AutoBoilerEncoder(driveAuto, robotPosition, target,  shooter, gearPickup);
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

		//Logger.getInstance().log("start auto");
	}

	public void autonomousPeriodic() {
		mAutoProgram.tick();
		driveAuto.tick();
		driveAuto.showEncoderValues();
		target.displayDetails();
		shooter.tick();
	}

	@Override
	public void testPeriodic() {
	}
	
	private double RampChange(double calledForSpeed, double lastSpeedSetting) {
		double newSpeed = 0;
		double rampRate = 0;
		
		rampRate = SmartDashboard.getNumber("Ramp Rate", .08);
		
		newSpeed = calledForSpeed;
		
		if (calledForSpeed >= lastSpeedSetting) {
			if (calledForSpeed - lastSpeedSetting > .1) {
				newSpeed = lastSpeedSetting + rampRate;
			}
		} else
		{
			if (lastSpeedSetting - calledForSpeed > .1 ) {
				newSpeed = lastSpeedSetting - rampRate;
			}
			
		}
		
		SmartDashboard.putNumber("Ramp Adjust Amount", calledForSpeed - newSpeed);
		
		return newSpeed;
	}
}
