package com.coderedrobotics;

public class Wiring {

	// PWM MOTOR CONTROLLERS
	public static final int CLIMBER_MOTOR = 0;
	public static final int GEAR_PICKUP_FINGERS = 1;

	public static final int CAMERA_SERVO = 3;
	public static final int AGITATOR_MOTOR = 4;
	
	public static final int RIGHT_DRIVE_MOTOR1 = 6;
	public static final int RIGHT_DRIVE_MOTOR2 = 7;
	public static final int LEFT_DRIVE_MOTOR1 = 8;
	public static final int LEFT_DRIVE_MOTOR2 = 9;
	
	// MOTOR CONTROLLER PDP PORTS
	//public static final int CLIMBER_PDP = 13;  // not used currently
	//public static final int PICKUP_FINGER_PDP = 15;  // not verified

	// CAN MOTOR CONTROLLERS
	public static final int ARM_MOTOR = 1;
	public static final int BALL_PICKUP_LEADER = 3;
	public static final int SHOOTER_FEEDER_MOTOR = 4;
	public static final int SHOOTER_MOTOR_LEADER = 5;
	public static final int SHOOTER_MOTOR_FOLLOWER = 6;
	public static final int GEAR_PICKUP_ARM = 7;
	public static final int BALL_PICKUP_FOLLOWER = 8;

	// DIGITAL INPUT
	public static final int LEFT_ENCODER_A = 0;
	public static final int LEFT_ENCODER_B = 1;
	public static final int RIGHT_ENCODER_A = 3;
	public static final int RIGHT_ENCODER_B = 2;
	public static final int FINGER_ENCODER_A = 4;
	public static final int FINGER_ENCODER_B = 5;

	// ANALOG INPUT
	//public static final int GEAR_IR_SENSOR = 0;  // not used yet

	// Relays
	public static final int LIGHT_RING_RELAY = 0;
	
}
