package com.coderedrobotics;

import edu.wpi.first.wpilibj.VictorSP;

// ball feeder agitator
// used only by Shooter class

public class Agitator {
	VictorSP agitatorTopMotors = new VictorSP(Wiring.AGITATOR_TOP_MOTORS);
	VictorSP agitatorBottomMotors = new VictorSP(Wiring.AGITATOR_BOTTOM_MOTORS);
	
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
				agitatorTopMotors.set(0.5);
			}else{
				agitatorTopMotors.set(-0.5);
			}
			agitatorBottomMotors.set(0.5);
		}else{
			agitatorTopMotors.set(0);
			agitatorBottomMotors.set(0);
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
