package com.coderedrobotics;

public class Calibration {

	public static final double PICKUP_FRONT_CURRENT_THRESHOLD = 20;
	public static final double PICKUP_REAR_CURRENT_THRESHOLD = 2;
	public static final int PICKUP_REAR_CURRENT_TIMEOUT = 200;

	public static final double SHOOTER_P = -0.0001;
	public static final double SHOOTER_I = -.75;
	public static final double SHOOTER_D = -0.001;
	public static final double SHOOTER_F = -1d / 50000d;
	
	public static final double FEEDER_P = .01;
	public static final double FEEDER_I = 0;
	public static final double FEEDER_D = 0;
	public static final double FEEDER_F = 0;
}
