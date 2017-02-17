package com.coderedrobotics;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.VictorSP;

public class GearPickup {
	
	VictorSP gearPickupFinger;
	CANTalon gearPickupArm;
	boolean isReleased;
	
	
	public GearPickup(){
		gearPickupFinger = new VictorSP(Wiring.GEAR_PICKUP_FINGERS);
		gearPickupArm = new CANTalon(Wiring.GEAR_PICKUP_ARM);
		gearPickupArm.setPID(Calibration.GEAR_PICKUP_ARM_P, Calibration.GEAR_PICKUP_ARM_I, Calibration.GEAR_PICKUP_ARM_D);
		gearPickupArm.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		
	}
	
	
	public void releasePickup() {
		// Lift gear pickup up far enough to pick up the ball mechanism.
		isReleased = true;
		gearPickupArm.set(Calibration.GEAR_PICKUP_ARM_SETPOINT);
		
	}
	
	public void readyPosition() {
		//Park the arm to ready position
		//need to test for the setPoint value
		//and then put the value in Calibration file
		gearPickupArm.setSetpoint(1);
	}
	
	public void pickUpGear() {
		// Reach down the rest of the way and pick up the gear
		gearPickupFinger.set(0.2);
		
	}
	
	public void verticalArm() {
		// Arm goes all the way up to gear placing thingy
	}
	
	public void releaseGear(){
		// Releases gear
	}
	
	public void park() {
		// Parks to original stored position
		//need to test for the setPoint value, 
		//and then put the value in Calibration file
		gearPickupArm.setSetpoint(0);
	}
	
}
