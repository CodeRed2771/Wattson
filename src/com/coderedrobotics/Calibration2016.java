package com.coderedrobotics;

/**
 *
 * @author michael
 */
public class Calibration2016 {
    
    public static final double PICKUP_INTAKE_SPEED = .75;
    public static final double PICKUP_OUTPUT_SPEED  = 1;
    public static final double PICKUP_NUDGE_SPEED = .55;
    public static final double PICKUP_FRONT_CURRENT_THRESHOLD = 20;
    public static final double PICKUP_SHOOTER_DROP_SPEED = .7;
    public static final int PICKUP_FRONT_CURRENT_TIMEOUT = 0;
    public static final int PICKUP_FRONT_CURRENT_IGNORE_DURATION = 1200; // in ms
    public static final double PICKUP_REAR_CURRENT_THRESHOLD = 2;
    public static final int PICKUP_REAR_CURRENT_TIMEOUT = 200;
    public static final int PICKUP_REAR_CURRENT_IGNORE_DURATION = 1000;
    
    public static final double ARM_SETPOINT_INCREMENT = .001;
    public static final double ARM_CALIBRATION_MOTOR_SPEED = .6; // was .3 3/10/16
    public static final double ARM_SHOOT_POSITION = 1.455810546875;
    public static final double ARM_P = 8;
    public static final double ARM_I = 0;
    public static final double ARM_D = 10;
    public static final double ARM_F = 0;

    public static final double ARM_MIN_SETPOINT = -2.3;
    public static final double ARM_MAX_SETPOINT = 0;
    public static final double ARM_PID_TEST_TOLERANCE = 0.1;

    public static final double DRIVE_TOP_SPEED = 13;
    public static final double DRIVE_P = 1;
    public static final double DRIVE_I = 0;
    public static final double DRIVE_D = 0.6;

    public static final double DRIVE_DISTANCE_PER_PULSE = .073;
    public static final double DRIVE_DISTANCE_TICKS_PER_INCH = 16.276;
    
    public static final double ROT_TOP_SPEED = 10;
    public static final double ROT_P = 1;
    public static final double ROT_I = 0;
    public static final double ROT_D = 0;
    
    public static final double DRIVE_TRAIN_REDUCTION_FACTOR = 0.5;
    
    public static final double SHOOTER_ERROR_TOLERANCE = 1000; // is sped up
    public static final double SHOOTER_SPIN_SPEED = -32000;
    public static final long SHOOTER_STOP_TIMEOUT = 1000;
    public static final double SHOOTER_CURRENT_THRESHOLD = 10;
    public static final double SHOOTER_BALL_ERROR_THRESHOLD = 6000; // error required to autostop
    public static final double SHOOTER_AUTOSTOP_DELAY = 500;
    public static final double SHOOTER_P = -0.0001;
    public static final double SHOOTER_I = -.75;
    public static final double SHOOTER_D = -0.001;
    public static final double SHOOTER_F = -1d / 50000d;
}
