package com.coderedrobotics;

import edu.wpi.first.wpilibj.VictorSP;

public class Climber {
	VictorSP climberMotor;
	boolean isClimbing = false;
	
	public Climber(){
		climberMotor = new VictorSP(Wiring.CLIMBER);
		
	}
	
	public void start(){
		climberMotor.set(Calibration.CLIMBER_POWER);
		isClimbing = true;
	}
	
	public void stop(){
		climberMotor.set(0);
		isClimbing = false;
	}
	public boolean isStalled(){
		//PUT IN CODE TO DETECT CURRENT DRAW
		return false;
	}
	public void tick() {
		if (isClimbing && isStalled()){
			stop();
		}
	}
}
