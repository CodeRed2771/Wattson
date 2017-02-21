package com.coderedrobotics;

import edu.wpi.first.wpilibj.VictorSP;

// ball feeder agitator
// used only by Shooter class

public class Agitator {
	VictorSP bottomMotor1 = new VictorSP(Wiring.AGITATOR_BOTTOM_MOTOR1);
	
	public boolean agitatorRunning = false;
	
	public void start() {
		bottomMotor1.set(.5);
		agitatorRunning = true;
	}
	
	public void stop() {
		bottomMotor1.set(0);
		agitatorRunning = false;
	}

	public void toggleAgitator() {
		if(agitatorRunning) {
			start();
		}else{
			stop();
		}
	}
	
}
