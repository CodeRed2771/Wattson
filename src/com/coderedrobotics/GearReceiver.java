package com.coderedrobotics;

import com.coderedrobotics.libs.CurrentBreaker;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Victor;

public class GearReceiver {
	Victor gearReceiver = new Victor(Wiring.GEAR_RECEIVER_MOTOR);
	AnalogInput irSensor = new AnalogInput(Wiring.GEAR_IR_SENSOR);
	CurrentBreaker gearReceiverBreaker = new CurrentBreaker(null, Wiring.GEAR_RECEIVER_PDP,
			Calibration.GEAR_RECEIVER_POWER, Calibration.GEAR_RECEIVER__TIMEOUT);
	boolean isExtended = false;
	boolean isExtending = false;
	boolean isRetracting = false;

	public void extend() {
		gearReceiver.set(5);
		isExtending = true;
		isRetracting = false;
		
	}

	public void retract() {
		gearReceiver.set(-5);
		isExtending = false;
		isRetracting = true;
	}

	public boolean gearPresent() {
		return (irSensor.getAverageVoltage() < Calibration.GEAR_PRESENT_VOLTAGE);
	}

	public boolean isExtended() {
		return isExtended;
	}
	
	public void toggleReceiver() {
		isExtended = !isExtended;
		if (isExtended()) {
			retract();
		}else{
			extend();
		}
	}

	public void tick() {
		if (isExtending && gearReceiverBreaker.tripped()) {
			gearReceiver.set(0);
			isExtended = true;
			isExtended = false;
			isRetracting = false;
		}
		if (isRetracting && gearReceiverBreaker.tripped()) {
			gearReceiver.set(0);
			isExtended = false;
			isExtending = true;
			isRetracting = false;
		}
	}
}