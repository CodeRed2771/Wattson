package com.coderedrobotics;

import edu.wpi.first.wpilibj.VictorSP;

// ball feeder agitator
// used only by Shooter class

public class Agitator {
	VictorSP agitatorMotor = new VictorSP(Wiring.AGITATOR_MOTOR);
	
	public boolean agitatorRunning = false;
	
	public void start() {
		agitatorRunning = true;
	}
	
	public void stop() {
		agitatorRunning = false;
	}
	
	public void tick() {
		if (agitatorRunning) {
			if ((System.currentTimeMillis() % 1000) >= 500) {
				agitatorMotor.set(0.5);
			}else{
				agitatorMotor.set(-0.5);
			}
		}else{
			agitatorMotor.set(0);
		}
	}

	public void toggleAgitator() {
		if(agitatorRunning) {
			start();
		}else{
			stop();
		}
	}
	
}
