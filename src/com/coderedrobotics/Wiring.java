package com.coderedrobotics;

public class Wiring {

	// PWM MOTOR CONTROLLERS
	public static final int LEFT_DRIVE_MOTOR1 = 8;
	public static final int LEFT_DRIVE_MOTOR2 = 9;
	public static final int RIGHT_DRIVE_MOTOR1 = 6;
	public static final int RIGHT_DRIVE_MOTOR2 = 7;
	public static final int GEAR_RECEIVER_MOTOR = 2;
	public static final int GEAR_PICKUP_FINGERS = 1;
	public static final int AGITATOR_TOP_MOTOR = 3;
	public static final int AGITATOR_BOTTOM_MOTOR1 = 4;
	public static final int AGITATOR_BOTTOM_MOTOR2 = 5;
	public static final int CLIMBER_MOTOR = 0;
	
	// MOTOR CONTROLLER PDP PORTS
	public static final int PICKUP_FRONT_PDP = 10;
	public static final int PICKUP_REAR_PDP = 11;
	public static final int SHOOTER_PDP = 12;
	public static final int CLIMBER_PDP = 13;
	public static final int GEAR_RECEIVER_PDP = 14;
	public static final int PICKUP_FINGER_PDP = 15;

	// CAN MOTOR CONTROLLERS
	public static final int ARM_MOTOR = 1;
	public static final int SHOOTER_MOTOR_1 = 2;
	public static final int SWEEPER_MOTOR = 3;
	public static final int SHOOTER_MOTOR_FEEDER = 4;
	public static final int SHOOTER_MOTOR_SHOOTER = 5;
	public static final int SHOOTER_MOTOR_FOLLOWER = 6;
	public static final int GEAR_PICKUP_ARM = 7;
	public static final int SWEEPER_MOTOR_FOLLOWER = 8;

	// DIGITAL INPUT
	public static final int LEFT_ENCODER_A = 0;
	public static final int LEFT_ENCODER_B = 1;
	public static final int RIGHT_ENCODER_A = 2;
	public static final int RIGHT_ENCODER_B = 3;
	public static final int FINGER_ENCODER_A = 4;
	public static final int FINGER_ENCODER_B = 5;

	// ANALOG INPUT
	public static final int GEAR_IR_SENSOR = 0;

	// Relays
	public static final int LIGHT_RING_RELAY = 0;
	
}
