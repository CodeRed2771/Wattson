package com.coderedrobotics;

import edu.wpi.first.wpilibj.VictorSP;

// ball feeder agitator
// used only by Shooter class

public class Agitator {
	VictorSP agitatorMotor = new VictorSP(Wiring.AGITATOR_MOTOR);
	
	public boolean agitatorRunning = false;
	
	public void start() {
		agitatorMotor.set(.5);
		agitatorRunning = true;
	}
	
	public void stop() {
		agitatorMotor.set(0);
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
