package com.coderedrobotics;

import edu.wpi.first.wpilibj.Victor;

public class Agitator {
	Victor agitatorMotor = new Victor(Wiring.AGITATOR_MOTOR);
	public boolean agitatorRunning = false;
	
	public void startAgitator() {
		agitatorMotor.set(.5);
		agitatorRunning = true;
	}
	
	public void stopAgitator() {
		agitatorMotor.set(0);
		agitatorRunning = false;
	}

	public void toggle() {
		if(agitatorRunning) {
			startAgitator();
		}else{
			stopAgitator();
		}
	}
	
}
