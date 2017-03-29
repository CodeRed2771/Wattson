package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.TalonSRX;

public class GearReceiver {
	
	CANTalon gearReceiverLeader;
	CANTalon gearReceiverFollower;
	CurrentBreaker currentBreaker;
	double gearCloseTime = 0;
	boolean isOpening = false;
	
	public GearReceiver(){
		gearReceiverLeader = new CANTalon(Wiring.GEAR_RECEIVER_LEADER);
		gearReceiverFollower = new CANTalon(Wiring.GEAR_RECEIVER_FOLLOWER);
		currentBreaker = new CurrentBreaker(null, (Integer) null, .5, 0, 0); //relook at this!!!
		
		gearReceiverLeader.configNominalOutputVoltage(0.0f,0.0f);
		gearReceiverLeader.configPeakOutputVoltage(0, 1);
		gearReceiverLeader.setProfile(0);
		
		gearReceiverFollower.changeControlMode(TalonControlMode.Follower);
		gearReceiverFollower.set(gearReceiverFollower.getDeviceID());
	}
	
	public void openGearCatch(){
		isOpening = true;
		gearReceiverLeader.set(.5);
		gearCloseTime = System.currentTimeMillis();
	}
	public void closeGearCatch(){
		gearReceiverLeader.set(-.5);
	}
	
	public void tick(){
		if(currentBreaker.tripped()){
			gearReceiverLeader.set(0);
		}
		
		if(System.currentTimeMillis() > gearCloseTime+5000){
			closeGearCatch();
			isOpening = false;
		}
	}
}
