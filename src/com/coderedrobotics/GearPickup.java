package com.coderedrobotics;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearPickup {

	VictorSP gearPickupFinger;
	CANTalon gearPickupArm;
	Encoder fingersEncoder;

	boolean isPickingUp;
	boolean isReleasing;
	double fingersStartTime;
	double fingersEncLastPosition;
	boolean isHorizontal;

	public GearPickup() {
		isPickingUp = false;

		isHorizontal = false;
		gearPickupFinger = new VictorSP(Wiring.GEAR_PICKUP_FINGERS);
		gearPickupArm = new CANTalon(Wiring.GEAR_PICKUP_ARM);

		gearPickupArm.setPID(Calibration.GEAR_PICKUP_ARM_P, Calibration.GEAR_PICKUP_ARM_I,
				Calibration.GEAR_PICKUP_ARM_D);
		gearPickupArm.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		gearPickupArm.changeControlMode(TalonControlMode.Position);
		gearPickupArm.configPeakOutputVoltage(3, -3);

		fingersEncoder = new Encoder(Wiring.FINGER_ENCODER_A, Wiring.FINGER_ENCODER_B);

	}

	public void disable() {
		isPickingUp = false;
		isHorizontal = false;
		park();
		stopFingers();
	}

	private void setVertical() {
		gearPickupArm.set(Calibration.GEAR_PICKUP_ARM_VERTICAL);
		isHorizontal = false;
	}

	private void setHorizontal() {
		gearPickupArm.set(Calibration.GEAR_PICKUP_ARM_HORIZONTAL);
		isHorizontal = true;
	}

	public void park() {
		// Parks to original stored position
		gearPickupArm.set(Calibration.GEAR_PICKUP_ARM_PARK);
	}

	public void releaseGear() {
		// reverse the fingers briefly, gets stopped in tick method
		gearPickupFinger.set(0.4);
		isReleasing = true;
		isPickingUp = false;
		fingersEncLastPosition = Math.abs(fingersEncoder.get());
	}

	private void stopFingers() {
		gearPickupFinger.set(0);
		isReleasing = false;
	}
	
	public void stop() {
		isPickingUp = true;
		gearPickupArm.set(Calibration.GEAR_PICKUP_ARM_HORIZONTAL);
		isHorizontal = true;
		fingersStartTime = System.currentTimeMillis();
		fingersEncLastPosition = Math.abs(fingersEncoder.get());
		gearPickupFinger.set(-0.6);
	}

	public void toggleArm() {
		if (isHorizontal) {
			setVertical();
			stopFingers();
		} else {
			// Reach down and start trying to pick up the gear
			stop();
		}
	}

	public void tick() {
		SmartDashboard.putNumber("Gear Pickup Position: ", gearPickupArm.getPosition());
		SmartDashboard.putNumber("Finger Position: ", fingersEncoder.get()*-1);
		SmartDashboard.putNumber("Last Finger Position: ", fingersEncLastPosition);

		if (isPickingUp) {
			// check encoders periodically to see if they are still moving. If
			// not, we have a gear
			if (System.currentTimeMillis() > fingersStartTime + 300) {
				if (Math.abs(fingersEncoder.get()) - fingersEncLastPosition < 25) {
					gearPickupFinger.set(0.40);
					setVertical();
					isPickingUp = false;
				} else {
					fingersStartTime = System.currentTimeMillis();
					fingersEncLastPosition = Math.abs(fingersEncoder.get());
				}
			}
		}

		if (isReleasing) {
			// run the fingers backwards for 25 ticks to release gear
			if (Math.abs(fingersEncoder.get()) < (fingersEncLastPosition - 25)) {
				stopFingers();
			}
		}

		//gearPickupArm.setP(SmartDashboard.getNumber("Arm P", Calibration.GEAR_PICKUP_ARM_P));
	}
}
