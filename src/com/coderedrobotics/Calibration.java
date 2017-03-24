package com.coderedrobotics;

public class Calibration {

	// PID
	public static final double SHOOTER_P = 0.06;
	public static final double SHOOTER_I = 0;
	public static final double SHOOTER_D = 0.5;
	public static final double SHOOTER_F = 0.025; // .03 = 1000 // .028 for 2000
	public static final double SHOOTER_SETPOINT = 3425;

	public static final double FEEDER_P = 0.1;
	public static final double FEEDER_I = 0;
	public static final double FEEDER_D = 0;
	public static final double FEEDER_F = 0;
	public static final double FEEDER_SETPOINT = 2800;

	public static final double GEAR_PICKUP_ARM_P = 4;
	public static final double GEAR_PICKUP_ARM_I = 0;
	public static final double GEAR_PICKUP_ARM_D = 0;
	public static final double GEAR_PICKUP_ARM_F = 0;

	public static final double BALL_PICKUP_P = 0.00004;
	public static final double BALL_PICKUP_I = 0.2;
	public static final double BALL_PICKUP_D = 0;

	public static final double DRIVE_TOP_SPEED = 30;
	public static final double DRIVE_P = 1;
	public static final double DRIVE_I = 0;
	public static final double DRIVE_D = 0.6;

	public static final double ROT_TOP_SPEED = 21;
	public static final double ROT_P = 3;
	public static final double ROT_I = 2.5;
	public static final double ROT_D = 1;

	public static final double AUTO_ROT_P = 0.03; // increased from .022 on 3/20/17 dvv
	public static final double AUTO_ROT_I = 0;
	public static final double AUTO_ROT_D = 0.067;

	public static final double AUTO_DRIVE_P = 0.0023;
	public static final double AUTO_DRIVE_I = 0.7;
	public static final double AUTO_DRIVE_D = 0.013;

	// Drive Encoder
	public static final double DRIVE_DISTANCE_PER_PULSE = 0.073; // we don't
																	// actually
																	// use this
																	// one.
																	// Delete?
	public static final double DRIVE_DISTANCE_TICKS_PER_INCH = 20.1;

	// Useless Stuff
	public static final double DRIVE_TRAIN_REDUCTION_FACTOR = 0.5; // the code
																	// that uses
																	// this is
																	// commented
																	// out

	// Ball Pickup
	public static final double BALL_PICKUP_PARK_POSITION = 0.96;
	public static final int BALL_PICKUP_TICKS_PER_REV = 4096;
	public static final double BALL_PICKUP_SPEED = 0.35;

	// Gear Pickup Setpoints
	public static final double GEAR_PICKUP_ARM_HORIZONTAL = 0.992;
	public static final double GEAR_PICKUP_ARM_READY = 0.6;
	//public static final double GEAR_PICKUP_ARM_PARK = 0.45;
	public static final double GEAR_PICKUP_ARM_PARK = 0.60; // competition
	public static final double GEAR_PICKUP_ARM_VERTICAL = 0.7; // competition bot
	//public static final double GEAR_PICKUP_ARM_VERTICAL = 0.7; // practice bot

	// Peg Length is actually the distance between the camera and the target if
	// the
	// tip of the gear peg is in the plane of the gear that is sitting in the
	// robot.
	public static final double PEG_LENGTH = 19;
	public static final double LATERAL_CAMERA_OFFSET = 14;
}
