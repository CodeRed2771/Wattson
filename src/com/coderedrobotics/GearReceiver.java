package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearReceiver {
	VictorSP gearMotor;
	double gearCloseTime = 0;
	boolean isOpening = false;
	CurrentBreaker currentBreaker;
	
	public GearReceiver(){
		gearMotor = new VictorSP(Wiring.GEAR_RECEIVER_MOTOR);
		currentBreaker = new CurrentBreaker(null, Wiring.GEAR_RECEIVER_PDP, Calibration.GEAR_RECVR_MAX_CURRENT, 200, 400); 
	}
	
	public void openGearCatch(){
		isOpening = true;
		currentBreaker.reset();
		gearMotor.set(-.4);
		gearCloseTime = System.currentTimeMillis();
	}
	
	private void closeGearCatch(){
		currentBreaker.reset();
		gearMotor.set(.2);
	}
	
	public void tick(){
		if (isOpening && (System.currentTimeMillis() > gearCloseTime + 4000)) {
			closeGearCatch();
			isOpening = false;
		}
		
		if (currentBreaker.tripped()) {
			gearMotor.set(0);
		}
		
		SmartDashboard.putNumber("Current Breaker: ", currentBreaker.getCurrent());
	}
}
