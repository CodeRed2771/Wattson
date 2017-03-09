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
	boolean isReleasing;
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
		gearPickupArm.configPeakOutputVoltage(3, -3);

		fingersEncoder = new Encoder(Wiring.FINGER_ENCODER_A,Wiring.FINGER_ENCODER_B);
		
		SmartDashboard.putNumber("Arm P", Calibration.GEAR_PICKUP_ARM_P);
		SmartDashboard.putNumber("Arm Setpoint", Calibration.GEAR_PICKUP_ARM_SETPOINT);
//		verticalArm();
	}
	public void giveTheKrakenATurn(){
		hasGear = false;
		isPickingUp = false;
		isVertical = false;
		isHorizontal = false;
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
	
	public void pickUpGear() {
//		 Reach down the rest of the way and pick up the gear
		isPickingUp = true;
		hasGear = false;
		horizontalArm();
		pinchGear();
	}
	
	public boolean isPickedup(){
		return hasGear;
	}
	
	public void verticalArm() {
		gearPickupArm.set(.7);
		isVertical = true;
		isHorizontal = false;
	}
	
	public void horizontalArm() {
		gearPickupArm.set(.992);
		isHorizontal = true;
		isVertical = false;
	}
	public void park() {
		// Parks to original stored position
		//need to test for the setPoint value, 
		//and then put the value in Calibration file
		gearPickupArm.setSetpoint(.6);
	}
	public void releaseGear(){
		gearPickupFinger.set(-.2);  // start going backwards
		isReleasing = true;
		isPickingUp = false;
		fingersEncLastPosition = Math.abs(fingersEncoder.get());
	}
	
	public void stopFingers() {
		gearPickupFinger.set(0);
		isReleasing = false;
	}
	
	public void pinchGear() {
		fingersStartTime = System.currentTimeMillis();
		fingersEncLastPosition = Math.abs(fingersEncoder.get());
		gearPickupFinger.set(0.6);
	}
	
	public boolean isArmInPosition(){
		return Math.abs(gearPickupArm.getClosedLoopError()) < 20;
	}
	
	public void toggleArm() {
		if (isHorizontal){
			verticalArm();
		}else{
			pickUpGear();
		}
	}
	
	public void tick(){
		SmartDashboard.putNumber("Gear Pickup Position: ", gearPickupArm.getPosition());
		SmartDashboard.putNumber("Gear Pickup Pulse Width: ", gearPickupArm.getPulseWidthPosition() & 0xFFF);
		SmartDashboard.putNumber("Finger Position: ", fingersEncoder.get());
		SmartDashboard.putNumber("Last Finger Position: ", fingersEncLastPosition);
		
		if(isPickingUp){
			if(System.currentTimeMillis() > fingersStartTime + 300) {
				if(Math.abs(fingersEncoder.get()) - fingersEncLastPosition < 25){
					hasGear = true;
					gearPickupFinger.set(0.40);
					verticalArm();
					isPickingUp = false;
				}else{
					fingersStartTime = System.currentTimeMillis();
					fingersEncLastPosition = Math.abs(fingersEncoder.get());
				}
			}
		}	
		
		if (isReleasing) {
			if (Math.abs(fingersEncoder.get()) < (fingersEncLastPosition - 25)) { // run the fingers backwards for 100 ticks to release gear
				stopFingers();
			}
		}
		gearPickupArm.setP(SmartDashboard.getNumber("Arm P", Calibration.GEAR_PICKUP_ARM_P));
		SmartDashboard.putNumber("Arm Setpoint", Calibration.GEAR_PICKUP_ARM_SETPOINT);
		SmartDashboard.putNumber("Arm Error: ", gearPickupArm.getClosedLoopError());
	}
	
	
	
}
