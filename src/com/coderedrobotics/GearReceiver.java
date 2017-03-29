package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;

public class GearReceiver {
	VictorSP gearMotor;
	double gearCloseTime = 0;
	boolean isOpening = false;
	CurrentBreaker currentBreaker;
	
	public GearReceiver(){
		gearMotor = new VictorSP(Wiring.GEAR_RECEIVER_MOTOR);
		currentBreaker = new CurrentBreaker(null, Wiring.GEAR_RECEIVER_PDP, Calibration.GEAR_RECVR_MAX_CURRENT, 100, 200); //NOTE:  THIS PDP PORT IS NOT RIGHT!!!!!!!!!!!!!!!
	}
	
	public void openGearCatch(){
		isOpening = true;
		currentBreaker.reset();
		gearMotor.set(.3);
		gearCloseTime = System.currentTimeMillis();
	}
	
	private void closeGearCatch(){
		currentBreaker.reset();
		gearMotor.set(-.3);
	}
	
	public void tick(){
		if (System.currentTimeMillis() > gearCloseTime + 5000) {
			closeGearCatch();
			isOpening = false;
		}
		
		if (currentBreaker.tripped()) {
			gearMotor.set(0);
		}
	}
}
