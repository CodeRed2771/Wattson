package com.coderedrobotics;

import com.coderedrobotics.libs.AutoBaseClass;
import com.coderedrobotics.libs.Logger;
import com.coderedrobotics.libs.HID.HID;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Target target;
	Drive drive;
	DriveAuto driveAuto;
	boolean isDriving = false;
	SendableChooser autoChooser;
	final String autoPegDVV = "Peg DVV";
	final String autoPegCH = "Peg Caden";
	final String autoTargetTest = "Target Test";
	String autoSelected;
	AutoBaseClass mAutoProgram;

	KeyMap gamepad;
	Shooter shooter;
	GearReceiver gearReceiver;
	Climber climber;
	Pickup pickup;

	
	boolean shooterActive = false;

	public void robotInit() {
		target = new Target();
		drive = new Drive();
		driveAuto = new DriveAuto(drive);
		driveAuto.stop();
		driveAuto.resetEncoders();
		drive.set(0, 0);
		drive.setPIDstate(true);

		target.displayDetails();

		shooter = new Shooter();
		gearReceiver = new GearReceiver();
		climber = new Climber();
		pickup = new Pickup();

		driveAuto.showPIDValues();
		drive.disablePID();

		autoChooser = new SendableChooser();
		autoChooser.addObject(autoPegDVV, autoPegDVV);
		autoChooser.addObject(autoPegCH, autoPegCH);
		autoChooser.addDefault(autoTargetTest, autoTargetTest);

		SmartDashboard.putData("Auto choices", autoChooser);

		gamepad = new KeyMap();
	}

	public void teleopInit() {
		driveAuto.resetEncoders();
		drive.set(0, 0);
	}

	public void teleopPeriodic() {

		// Drive
		drive.set(gamepad.getLeftAxis(), gamepad.getRightAxis());

		// Shooter
		// Start/stop shooter
		if(gamepad.getShooterWheelButton()){
			shooter.toggleShooter();
		}
		// Start/stop shooter intake
		if (gamepad.getShooterIntake() && gamepad.getShooter()) {
			shooter.feedShooter();
		}
		// Agitator
		if (gamepad.getAgitatorButton()) {
			// insert code for agitator here
		}

		// Start Pickup
		if (gamepad.startPickup()) {
			pickup.sweeperStart();
		}

		// Gear receiver
		if (gamepad.gearRecieverExtend()) {

		}

		climber.tick();
		shooter.tick();
		pickup.tick();
	}

	public void autonomousInit() {
		drive.set(0, 0);
		drive.setPIDstate(true);

		autoSelected = (String) autoChooser.getSelected();
		SmartDashboard.putString("Auto selected: ", autoSelected);

		switch (autoSelected) {
		case autoTargetTest:
			mAutoProgram = new AutoTargetTest(driveAuto, 1);
			break;
		case autoPegDVV:
			mAutoProgram = new AutoPegDVV(driveAuto, 1);
			break;
		case autoPegCH:
			mAutoProgram = new AutoPegCH(driveAuto, 1);
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

	}

	@Override
	public void testPeriodic() {
	}
}
