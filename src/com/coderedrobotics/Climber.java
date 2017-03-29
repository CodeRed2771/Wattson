package com.coderedrobotics;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class Climber {
	CANTalon climbLeader;
	CANTalon climbFollower;

	public Climber() {

		climbLeader = new CANTalon(Wiring.CLIMB_LEADER);
		climbFollower = new CANTalon(Wiring.CLIMB_FOLLOWER);

		climbLeader.configNominalOutputVoltage(0.0f, 0.0f);
		climbLeader.configPeakOutputVoltage(13, 13);
		climbLeader.setProfile(0);

		climbFollower.changeControlMode(TalonControlMode.Follower);
		climbFollower.set(climbLeader.getDeviceID());
	}

	public void climb(double climbPower) {
		climbLeader.set(-Math.max(0, climbPower));
	}

}
