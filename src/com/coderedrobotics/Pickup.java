package com.coderedrobotics;

import com.coderedrobotics.libs.PWMController;

public class Pickup {

	private final PWMController leftExtender;
	private final PWMController rightExtender;
	private final PWMController sweeperMotor;
	
	private boolean pickingup = false;
	
	public Pickup() {
		leftExtender = new PWMController(Wiring.LEFT_EXTENDER,false);
		rightExtender = new PWMController(Wiring.RIGHT_EXTENDER, true);
		sweeperMotor = new PWMController(Wiring.SWEEPER_MOTOR,false);
		
				
	}
}
