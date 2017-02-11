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
	
	 public static final double DRIVE_TOP_SPEED = 13;
   public static final double DRIVE_P = 1;
   public static final double DRIVE_I = 0;
   public static final double DRIVE_D = 0.6;

   public static final double DRIVE_DISTANCE_PER_PULSE = .073;
   public static final double DRIVE_DISTANCE_TICKS_PER_INCH = 12.764;
   
   public static final double ROT_TOP_SPEED = 10;
   public static final double ROT_P = 1;
   public static final double ROT_I = 0;
   public static final double ROT_D = 0;
   
   public static final double DRIVE_TRAIN_REDUCTION_FACTOR = 0.5;
}
