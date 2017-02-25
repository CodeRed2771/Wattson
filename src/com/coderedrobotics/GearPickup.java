package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;
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
	boolean isReleased;
	boolean hasGear;
	boolean isPickingUp;
	double fingersStartTime;
	double fingersEncLastPosition;
//	CurrentBreaker fingerBreaker;
	
	public GearPickup(){
		hasGear = false;
		isPickingUp = false;
		gearPickupFinger = new VictorSP(Wiring.GEAR_PICKUP_FINGERS);
		gearPickupArm = new CANTalon(Wiring.GEAR_PICKUP_ARM);
		gearPickupArm.setPID(Calibration.GEAR_PICKUP_ARM_P, Calibration.GEAR_PICKUP_ARM_I, Calibration.GEAR_PICKUP_ARM_D);
		gearPickupArm.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		gearPickupArm.changeControlMode(TalonControlMode.Position);
		gearPickupArm.configPeakOutputVoltage(.1, -.1);

		fingersEncoder = new Encoder(Wiring.FINGER_ENCODER_A,Wiring.FINGER_ENCODER_B);

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
		gearPickupArm.set(.8);
	}
	
	public void pickupPosition() {
		// right on the gear, ready to squeeze fingers
		gearPickupArm.set(.95);
	}
	
	public void pickUpGear() {
		// Reach down the rest of the way and pick up the gear
		isPickingUp = true;
		hasGear = false;
		pickupPosition();
		pinchGear();
	}
	
	public boolean isPickedup(){
		return hasGear;
	}
	
	public void verticalArm() {
		gearPickupArm.set(.71);
	}
	
	public void releaseGear(){
		gearPickupFinger.set(-.2);
	}
	
	public void pinchGear() {
		fingersStartTime = System.currentTimeMillis();
		fingersEncLastPosition = Math.abs(fingersEncoder.get());
		gearPickupFinger.set(0.4);
	}
	
	public boolean isArmInPosition(){
		return Math.abs(gearPickupArm.getClosedLoopError()) < 20;
	}
	
	public void tick(){
		SmartDashboard.putNumber("Gear Pickup Position: ", gearPickupArm.getPosition());
		if(isPickingUp){
			if(System.currentTimeMillis() > fingersStartTime + 250){
				if(Math.abs(fingersEncoder.get()) - fingersEncLastPosition < 50){
					hasGear = true;
					gearPickupFinger.set(0.1);
					verticalArm();
					isPickingUp = false;
				}else{
					fingersStartTime = System.currentTimeMillis();
					fingersEncLastPosition = Math.abs(fingersEncoder.get());
				}
			}
		}
	}
	
	public void park() {
		// Parks to original stored position
		//need to test for the setPoint value, 
		//and then put the value in Calibration file
		gearPickupArm.setSetpoint(.55);
	}
	
}
