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
	boolean isVertical;
	boolean isHorizontal;
//	CurrentBreaker fingerBreaker;
	
	public GearPickup(){
		hasGear = false;
		isPickingUp = false;
		isVertical = false;
		isHorizontal = false;
		gearPickupFinger = new VictorSP(Wiring.GEAR_PICKUP_FINGERS);
		gearPickupArm = new CANTalon(Wiring.GEAR_PICKUP_ARM);
//		gearPickupArm.reset();
		gearPickupArm.setPID(Calibration.GEAR_PICKUP_ARM_P, Calibration.GEAR_PICKUP_ARM_I, Calibration.GEAR_PICKUP_ARM_D);
		gearPickupArm.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Absolute);
		gearPickupArm.changeControlMode(TalonControlMode.Position);
		gearPickupArm.configPeakOutputVoltage(2, -2);

		fingersEncoder = new Encoder(Wiring.FINGER_ENCODER_A,Wiring.FINGER_ENCODER_B);
		
		SmartDashboard.putNumber("Arm P", Calibration.GEAR_PICKUP_ARM_P);
		SmartDashboard.putNumber("Arm Setpoint", Calibration.GEAR_PICKUP_ARM_SETPOINT);
//		verticalArm();
	}
	
	public void releasePickup() {
		// Lift gear pickup up far enough to pick up the ball mechanism.
		isReleased = true;
		gearPickupArm.set(Calibration.GEAR_PICKUP_ARM_SETPOINT);
	}
	
	public void readyPosition() {
//		Park the arm to ready position
//		need to test for the setPoint value
//		and then put the value in Calibration file
		gearPickupArm.set(.6);
	}
	
	public void pickupPosition() {
		 //right on the gear, ready to squeeze fingers
		gearPickupArm.set(.9);
	}
	
	public void pickUpGear() {
//		 Reach down the rest of the way and pick up the gear
		isPickingUp = true;
		hasGear = false;
		pickupPosition();
		pinchGear();
	}
	
	public boolean isPickedup(){
		return hasGear;
	}
	
	public void verticalArm() {
		//gearPickupArm.set(-.38);
//		gearPickupArm.setPulseWidthPosition(2500);
		gearPickupArm.set(.596);
		isVertical = true;
		isHorizontal = false;
	}
	
	public void horizontalArm() {
//		gearPickupArm.setPulseWidthPosition(4070);
		gearPickupArm.set(.992);
		isHorizontal = true;
		isVertical = false;
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
	
	public void toggleArm() {
		if (isHorizontal){
			verticalArm();
		}else{
			horizontalArm();
		}
	}
	
	public void tick(){
		//SmartDashboard.putNumber("Gear Pickup Position: ", gearPickupArm.getPosition());
		SmartDashboard.putNumber("Gear Pickup Position: ", gearPickupArm.getPulseWidthPosition() & 0xFFF);
//		if(isPickingUp){
//			if(System.currentTimeMillis() > fingersStartTime + 250){
//				if(Math.abs(fingersEncoder.get()) - fingersEncLastPosition < 50){
//					hasGear = true;
//					gearPickupFinger.set(0.1);
//					verticalArm();
//					isPickingUp = false;
//				}else{
//					fingersStartTime = System.currentTimeMillis();
//					fingersEncLastPosition = Math.abs(fingersEncoder.get());
//				}
//			}
//		}
		SmartDashboard.putNumber("Arm P", Calibration.GEAR_PICKUP_ARM_P);
		SmartDashboard.putNumber("Arm Setpoint", Calibration.GEAR_PICKUP_ARM_SETPOINT);
	}
	
	public void park() {
		// Parks to original stored position
		//need to test for the setPoint value, 
		//and then put the value in Calibration file
		gearPickupArm.setSetpoint(.55);
	}
	
}
