package com.coderedrobotics;

/**
 *
 * @author michael
 */
public class Wiring {

    // PWM MOTOR CONTROLLERS
    public static final int LEFT_DRIVE_MOTOR1 = 0;
    public static final int LEFT_DRIVE_MOTOR2 = 1;
    public static final int RIGHT_DRIVE_MOTOR1 = 2;
    public static final int RIGHT_DRIVE_MOTOR2 = 3;
    public static final int RIGHT_EXTENDER = 4;
    public static final int LEFT_EXTENDER = 5;
    
    // MOTOR CONTROLLER PDP PORTS
    public static final int PICKUP_FRONT_PDP = 10;
    public static final int PICKUP_REAR_PDP = 11;
    public static final int SHOOTER_PDP = 12;

    // CAN MOTOR CONTROLLERS
    public static final int ARM_MOTOR = 1;
    public static final int SHOOTER_MOTOR_1 = 2;
    public static final int SWEEPER_MOTOR = 2;
    public static final int SHOOTER_MOTOR_FEEDER = 3;
    public static final int SHOOTER_MOTOR_SHOOTER = 4;
    
    // DIGITAL INPUT
    public static final int ARM_LIMIT_SWITCH = 0;
    public static final int LEFT_ENCODER_A = 2;
    public static final int LEFT_ENCODER_B = 1;
    public static final int RIGHT_ENCODER_A = 3;
    public static final int RIGHT_ENCODER_B = 4;

    // ANALOG INPUT
    
    // Relays
    public static final int RED_AND_GREEN_LEDS = 1;
    public static final int BLUE_LEDS = 0;
}
