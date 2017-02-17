package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Victor;

public class GearReciever {
	Victor gearReciever = new Victor(Wiring.GEAR_RECIEVER_MOTOR);
	AnalogInput irSensor = new AnalogInput(Wiring.GEAR_IR_SENSOR);
	CurrentBreaker gearRecieverBreaker = new CurrentBreaker(null, Wiring.GEAR_RECIEVER_PDP, Calibration.GEAR_RECIEVER_POWER, Calibration.GEAR_RECIEVER__TIMEOUT);
	boolean isExtended = false;
	
	public void extend() {
		gearReciever.set(5);
		isExtended = true;
	}

	public void retract() {
		gearReciever.set(-5);
		isExtended = false;
	}

	public boolean gearPresent() {
		return (irSensor.getAverageVoltage()< Calibration.GEAR_PRESENT_VOLTAGE);
	}
	
	public boolean isExtended(){
		return isExtended;
	}
	
	public void tick(){
		
	}
}