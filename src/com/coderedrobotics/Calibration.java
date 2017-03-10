package com.coderedrobotics;

public class Calibration {

	public static final double SHOOTER_P = 0.08;
	public static final double SHOOTER_I = 0;
	public static final double SHOOTER_D = 4.0;
	public static final double SHOOTER_F = 0.025;   // .03 = 1000   // .028 for 2000
	public static final double SHOOTER_SETPOINT = 3600;

	public static final double FEEDER_P = .1;
	public static final double FEEDER_I = 0;
	public static final double FEEDER_D = 0;
	public static final double FEEDER_F = 0;
	public static final double FEEDER_SETPOINT = 3000;

	public static final double DRIVE_TOP_SPEED = 30; //no idea on what the range is
	public static final double DRIVE_P = 1;
	public static final double DRIVE_I = 0;
	public static final double DRIVE_D = 0.6;
	public static final double ROT_TOP_SPEED = 21;
	public static final double ROT_P = 3;
	public static final double ROT_I = 2.5;
	public static final double ROT_D = 1;

	public static final double GEAR_PICKUP_ARM_P = 4;
	public static final double GEAR_PICKUP_ARM_I = 0;
	public static final double GEAR_PICKUP_ARM_D = 0;
	public static final double GEAR_PICKUP_ARM_f = 0;
	public static final double GEAR_PICKUP_ARM_SETPOINT = 0;

    public static final double AUTO_GYRO_P = 0.022; 
    public static final double AUTO_GYRO_I = 0; 
    public static final double AUTO_GYRO_D = 0.067; 

    public static final double AUTO_DRIVE_P = 0.0023; 
    public static final double AUTO_DRIVE_I = 0.7;
    public static final double AUTO_DRIVE_D = 0.013;

	public static final double DRIVE_DISTANCE_PER_PULSE = .073;
	public static final double DRIVE_DISTANCE_TICKS_PER_INCH = 20.1;

	public static final double DRIVE_TRAIN_REDUCTION_FACTOR = 0.5;
	public static final double CLIMBER_POWER = .75;
	public static final double GEAR_PRESENT_VOLTAGE = .5;
	public static final double GEAR_RECEIVER_POWER = .75;
	
	public static final int PICKUP_PARK_POSITION = 2000;  // just a  guess
	
	public static final double PEG_LENGTH = 19; //This is actually the distance between the camera and the 
	//target if the tip of the gear peg is in the plane of the gear that is sitting in the robot.
	public static final double LATERAL_CAMERA_OFFSET = 14;
	
//	public static final double CLIMBER_CURRENT_THRESHOLD = 20;
//	public static final int CLIMBER_CURRENT_TIMEOUT = 200;
//	public static final int CLIMBER_CURRENT_IGNORE_DURATION = 200;
//	public static final int GEAR_RECEIVER__TIMEOUT = 200;
//
//	public static final double PICKUP_FINGER_CURRENT_THRESHOLD = 20;
//	public static final int PICKUP_FINGER_CURRENT_TIMEOUT = 200;
//	public static final int PICKUP_FINGER_CURRENT_DURATION = 200;


}
