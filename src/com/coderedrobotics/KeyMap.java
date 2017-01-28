package com.coderedrobotics;

import com.coderedrobotics.libs.HID.HID;
import com.coderedrobotics.libs.HID.LogitechF310;

/**
 *
 * @author michael
 */
public class KeyMap {

    // GAMEPADS
    private final HID gp1 = new HID(0);
    private final HID gp2 = new HID(1);
    private final int gamepad1 = 0;
    private final int gamepad2 = 1;

    // MANAGEMENT BOOLEANS
    private boolean reverseDrive = false;
    private boolean singleControllerMode = false;
    private boolean reduceSpeed = false;

    // CONTROLLER 0
    private final HID.Button reverseDriveButton = LogitechF310.BUMPER_LEFT;
    private final HID.Button reduceSpeedButton = LogitechF310.BUMPER_RIGHT;
    private final HID.Button singleControllerModeButton = LogitechF310.STICK_RIGHT;
    private final HID.Axis driveLeftAxis = LogitechF310.STICK_LEFT_Y;
    private final HID.Axis driveRightAxis = LogitechF310.STICK_RIGHT_Y;
    private final HID.Button fireButton = LogitechF310.TRIGGER_RIGHT;
    private final HID.Button fireOverrideButton = LogitechF310.TRIGGER_LEFT;
    private final HID.Button cancelFireButton = LogitechF310.X;
    
    // CONTROLLER 1
    private final HID.Button feedInButton = LogitechF310.A;
    private final HID.Button feedOutButton = LogitechF310.B;
    private final HID.Button feedStopButton = LogitechF310.X;
    private final HID.Button gotoShooterPositionButton = LogitechF310.Y;
    private final HID.Button overrideArmPIDButton = LogitechF310.DPAD_UP;
    private final HID.Button overrideDrivePIDButton = LogitechF310.DPAD_LEFT;
    private final HID.Button overrideShooterPIDButton = LogitechF310.DPAD_DOWN;
    private final HID.Axis armAxis = LogitechF310.STICK_LEFT_Y;

    // BUTTON STATES
    private final HID.ButtonState reverseDriveButtonState = HID.newButtonState();
    private final HID.ButtonState singleControllerModeState = HID.newButtonState();
    private final HID.ButtonState reduceSpeedButtonState = HID.newButtonState();

    public KeyMap() {

    }

    private HID getHID(int gamepad) {
        if (!singleControllerMode) {
            switch (gamepad) {
                case gamepad1:
                    return gp1;
                case gamepad2:
                    return gp2;
                default:
                    return null;
            }
        } else {
            return gp1;
        }
    }

    public boolean getReverseDriveButton() {
        return getHID(gamepad1).buttonPressed(reverseDriveButton, reverseDriveButtonState);
    }

    public void toggleReverseDrive() {
        reverseDrive = !reverseDrive;
    }

    public boolean getReverseDrive() {
        return reverseDrive;
    }

    public boolean getReduceSpeedButton() {
        return getHID(gamepad1).buttonPressed(reduceSpeedButton, reduceSpeedButtonState);
    }

    public void toggleReduceSpeed() {
        reduceSpeed = !reduceSpeed;
    }

    public boolean getReduceSpeed() {
        return reduceSpeed;
    }

    public void setSingleControllerMode(boolean state) {
        singleControllerMode = state;
    }

    public boolean getSingleControllerMode() {
        return singleControllerMode;
    }

    public void toggleSingleControllerMode() {
        singleControllerMode = !singleControllerMode;
    }

    public boolean getSingleControllerToggleButton() {
        return getHID(gamepad1).buttonPressed(singleControllerModeButton, singleControllerModeState);
    }

    public boolean getFireButton() {
        return getHID(gamepad1).button(fireButton);
    }

    public boolean getFireOverrideButton() {
        return getHID(gamepad1).button(fireOverrideButton);
    }
    
    public double getLeftAxis() {
    	return (reverseDrive ? -(getHID(gamepad1).axis(driveRightAxis)) : (getHID(gamepad1).axis(driveLeftAxis))) *
    			(reduceSpeed ? Calibration.DRIVE_TRAIN_REDUCTION_FACTOR : 1);
    }
    
    public double getRightAxis() {
    	return (reverseDrive ? -(getHID(gamepad1).axis(driveLeftAxis)) : (getHID(gamepad1).axis(driveRightAxis))) *
    			(reduceSpeed ? Calibration.DRIVE_TRAIN_REDUCTION_FACTOR : 1);
    }
    
    public boolean getDriverCancelFireButton() {
        return getHID(gamepad1).button(cancelFireButton);
    }
    
    public double getArmAxis() {
        return -getHID(gamepad2).axis(armAxis);
    }

    public boolean getFeedInButton() {
        return getHID(gamepad2).button(feedInButton);
    }

    public boolean getFeedOutButton() {
        return getHID(gamepad2).button(feedOutButton);
    }

    public boolean getFeedStopButton() {
        return getHID(gamepad2).button(feedStopButton);
    }

    public boolean getGotoShootPositionButton() {
        return getHID(gamepad2).button(gotoShooterPositionButton);
    }

    public boolean getOverrideArmPIDButton() {
        return getHID(gamepad2).button(overrideArmPIDButton);
    }
    
    public boolean getOverrideDrivePIDButton() {
        return getHID(gamepad2).button(overrideDrivePIDButton);
    }
    
    public boolean getOverrideShooterPIDButton() {
        return getHID(gamepad2).button(overrideShooterPIDButton);
    }
}
