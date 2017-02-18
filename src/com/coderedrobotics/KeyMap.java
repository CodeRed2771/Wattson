package com.coderedrobotics;

import com.coderedrobotics.libs.HID.HID;
import com.coderedrobotics.libs.HID.HID.Axis;
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
    //private boolean shooter = false;
    //private boolean shooterWheels = false;
    //private boolean shooterWheelsIntake = false;

    // CONTROLLER 0
    private final HID.Button shooterIntakeButton = LogitechF310.TRIGGER_RIGHT;
    private final HID.Axis driveRightAxis = LogitechF310.STICK_RIGHT_Y;
    private final HID.Axis driveLeftAxis = LogitechF310.STICK_LEFT_Y;
    

    
    // CONTROLLER 1
    private final HID.Button shooterButton = LogitechF310.BUMPER_RIGHT;
    private final HID.Button agitatorButton = LogitechF310.B;
    private final HID.Button pickupButton = LogitechF310.A;
    private final HID.Button gearArmPickupButton = LogitechF310.BUMPER_LEFT;
    private final HID.Button gearFingersPickupOpenButton = LogitechF310.DPAD_LEFT;
    private final HID.Button gearFingersPickupCloseButton = LogitechF310.DPAD_RIGHT;
    private final HID.Button gearGateButton = LogitechF310.Y;
    

    // BUTTON STATES
    private final HID.ButtonState shooterButtonState = HID.newButtonState();
    private final HID.ButtonState agitatorButtonState = HID.newButtonState();
    private final HID.ButtonState pickupButtonState = HID.newButtonState();
    private final HID.ButtonState gearArmPickupButtonState = HID.newButtonState();
    //private final HID.ButtonState gearFingersPickupButtonState = HID.newButtonState();
    private final HID.ButtonState gearGateButtonState = HID.newButtonState();
    
    
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
    //NEW STUFF - 2017

    
    public boolean shooterWheels(){
    	return getHID(gamepad2).buttonPressed(shooterButton, shooterButtonState);
    }
    public boolean shooterIntake(){
    	return getHID(gamepad1).button(shooterIntakeButton);
    }
    public boolean gearGate() {
    	return getHID(gamepad2).buttonPressed(gearGateButton, gearGateButtonState);
    }
    public boolean gearFingersOpen() {
    	return getHID(gamepad2).button(gearFingersPickupOpenButton);
    }
    public boolean gearFingersClosed() {
    	return getHID(gamepad2).button(gearFingersPickupCloseButton);
    }
    public boolean agitator(){
    	return getHID(gamepad2).buttonPressed(agitatorButton, agitatorButtonState);
    }
    public boolean pickup(){
    	return getHID(gamepad2).buttonPressed(pickupButton, pickupButtonState);
    }
    public boolean gearArm(){
    	return getHID(gamepad2).buttonPressed(gearArmPickupButton, gearArmPickupButtonState);
    }
    
    //NEW STUFF - 2017

//    public boolean getReverseDriveButton() {
//        return getHID(gamepad1).buttonPressed(reverseDriveButton, reverseDriveButtonState);
//    }
//
//    public void toggleReverseDrive() {
//        reverseDrive = !reverseDrive;
//    }
//
//    public boolean getReverseDrive() {
//        return reverseDrive;
//    }
//
//    public boolean getReduceSpeedButton() {
//        return getHID(gamepad1).buttonPressed(reduceSpeedButton, reduceSpeedButtonState);
//    }
//
//    public void toggleReduceSpeed() {
//        reduceSpeed = !reduceSpeed;
//    }
//
//    public boolean getReduceSpeed() {
//        return reduceSpeed;
//    }
//
//    public void setSingleControllerMode(boolean state) {
//        singleControllerMode = state;
//    }
//
//    public boolean getSingleControllerMode() {
//        return singleControllerMode;
//    }
//
//    public void toggleSingleControllerMode() {
//        singleControllerMode = !singleControllerMode;
//    }
//
//    public boolean getSingleControllerToggleButton() {
//        return getHID(gamepad1).buttonPressed(singleControllerModeButton, singleControllerModeState);
//    }
//
//    public boolean getFireButton() {
//        return getHID(gamepad1).button(fireButton);
//    }
//
//    public boolean getFireOverrideButton() {
//        return getHID(gamepad1).button(fireOverrideButton);
//    }
//    
    public double getLeftAxis() {
    	return (reverseDrive ? -(getHID(gamepad1).axis(driveRightAxis)) : (getHID(gamepad1).axis(driveLeftAxis))) *
    			(reduceSpeed ? Calibration.DRIVE_TRAIN_REDUCTION_FACTOR : 1);
    	//return 0; // This is here so it is not mad
    }
    
    public double getRightAxis() {
    	return (reverseDrive ? -(getHID(gamepad1).axis(driveLeftAxis)) : (getHID(gamepad1).axis(driveRightAxis))) *
    			(reduceSpeed ? Calibration.DRIVE_TRAIN_REDUCTION_FACTOR : 1);
    	//return 0; // this is also here so that it is not mad
    }
//    
//    public boolean getDriverCancelFireButton() {
//        return getHID(gamepad1).button(cancelFireButton);
//    }
//    
//    public double getArmAxis() {
//        return -getHID(gamepad2).axis(armAxis);
//    }
//
//    public boolean getFeedInButton() {
//        return getHID(gamepad2).button(feedInButton);
//    }
//
//    public boolean getFeedOutButton() {
//        return getHID(gamepad2).button(feedOutButton);
//    }
//
//    public boolean getFeedStopButton() {
//        return getHID(gamepad2).button(feedStopButton);
//    }
//
//    public boolean getGotoShootPositionButton() {
//        return getHID(gamepad2).button(gotoShooterPositionButton);
//    }
//
//    public boolean getOverrideArmPIDButton() {
//        return getHID(gamepad2).button(overrideArmPIDButton);
//    }
//    
//    public boolean getOverrideDrivePIDButton() {
//        return getHID(gamepad2).button(overrideDrivePIDButton);
//    }
//    
//    public boolean getOverrideShooterPIDButton() {
//        return getHID(gamepad2).button(overrideShooterPIDButton);
//    }
}
