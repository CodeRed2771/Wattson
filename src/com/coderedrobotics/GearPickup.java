package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.VictorSP;

public class GearPickup {
	
	VictorSP gearPickupFinger;
	CANTalon gearPickupArm;
	boolean isReleased;
	boolean hasGear;
//	CurrentBreaker fingerBreaker;
	
	public GearPickup(){
		hasGear = false;
		gearPickupFinger = new VictorSP(Wiring.GEAR_PICKUP_FINGERS);
		gearPickupArm = new CANTalon(Wiring.GEAR_PICKUP_ARM);
		gearPickupArm.setPID(Calibration.GEAR_PICKUP_ARM_P, Calibration.GEAR_PICKUP_ARM_I, Calibration.GEAR_PICKUP_ARM_D);
		gearPickupArm.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
//		fingerBreaker = new CurrentBreaker(null, Wiring.PICKUP_FINGER_PDP, Calibration.PICKUP_FINGER_CURRENT_THRESHOLD,
//				Calibration.PICKUP_FINGER_CURRENT_TIMEOUT, Calibration.PICKUP_FINGER_CURRENT_DURATION);	
//		
//		fingerBreaker.reset();
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
	
	public void pickupPosition() {
		// right on the gear, ready to squeeze fingers
	}
	
	public void pickUpGear() {
		// Reach down the rest of the way and pick up the gear
		pickupPosition();
		pinchGear();
	}
	
	public boolean isPickedup(){
		return hasGear;
	}
	
	public void verticalArm() {
		// Arm goes vertical - ready to place gear
	}
	
	public void releaseGear(){
		gearPickupFinger.set(-.2);
//		fingerBreaker.reset();
	}
	
	public void pinchGear() {
//		fingerBreaker.reset();
		gearPickupFinger.set(0.2);
	}
	
	public void tick(){
		//check if the fingers have gear
//		if(fingerBreaker.tripped()){
//			hasGear = true;
//			gearPickupFinger.set(0.01); // keep light pressure on fingers
//			verticalArm(); // lift up as soon as we have gear
//		}
//		
	}
	
	public void park() {
		// Parks to original stored position
		//need to test for the setPoint value, 
		//and then put the value in Calibration file
		gearPickupArm.setSetpoint(0);
	}
	
}
