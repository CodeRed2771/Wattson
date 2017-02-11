package com.coderedrobotics;

public class Pickup {

	private final PWMController leftExtender;
	private final PWMController rightExtender;
	private final PWMController mainMotor;
	
	private boolean picking up = false;
	
	public Pickup(int leftExtender, int rightExtender, Function pickupEvent) {
		leftExtender = new PWMController(leftExtender,false);
		rightExtender = new PWMController(rightExtender, true);
		mainMotor = new PWMController(null, wiring.PICKUP_FRONT_DPD.
				Calibration.PICKUP_FRONT_CURRENT_THRESHOLD,
				Calibration.PICKUP_FRONT_CURRENT_TIMEOUT,
				Calibration.PICKUP_FRONT_CURRENT_IGNORE_DURATION);
		
				
	}
}
