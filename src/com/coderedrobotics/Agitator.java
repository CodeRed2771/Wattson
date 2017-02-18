package com.coderedrobotics;

import edu.wpi.first.wpilibj.VictorSP;

// ball feeder agitator
// used only by Shooter class

public class Agitator {
	VictorSP bottomMotor1 = new VictorSP(Wiring.AGITATOR_BOTTOM_MOTOR1);
	VictorSP bottomMotor2 = new VictorSP(Wiring.AGITATOR_BOTTOM_MOTOR2);
	VictorSP topMotor = new VictorSP(Wiring.AGITATOR_TOP_MOTOR);
	
	public boolean agitatorRunning = false;
	
	public void start() {
		topMotor.set(.5);
		bottomMotor1.set(.5);
		bottomMotor2.set(.5);
		agitatorRunning = true;
	}
	
	public void stop() {
		topMotor.set(0);
		bottomMotor1.set(0);
		bottomMotor2.set(0);
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
