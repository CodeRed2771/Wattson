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
    private boolean shooter = false;
    
   
    // CONTROLLER 0
    private final HID.Button shooterButton = LogitechF310.BUMPER_RIGHT;
    private final HID.Button shooterIntakeButton = LogitechF310.BUMPER_LEFT;
    
    
    // CONTROLLER 1


    // BUTTON STATES
    private final HID.ButtonState shooterButtonState = HID.newButtonState();

    
    

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

    
    public boolean getShooterWheelButton(){
    	return getHID(gamepad1).buttonPressed(shooterButton, shooterButtonState);
    }
    public void toggleShooter(){
    	shooter = !shooter;
    }
    public boolean getShooter(){
    	return shooter;
    }
    public boolean getShooterIntake(){
    	return getHID(gamepad1).button(shooterIntakeButton);
    }
    public boolean gearRecieverExtend() {
    	return false;
    }
    public boolean gearRecieverRetract() {
    	return false;
    }
    public boolean pickUpGear() {
    	return false;
    }
    public boolean releaseGear() {
    	return false;
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
    	//return (reverseDrive ? -(getHID(gamepad1).axis(driveRightAxis)) : (getHID(gamepad1).axis(driveLeftAxis))) *
    			//(reduceSpeed ? Calibration.DRIVE_TRAIN_REDUCTION_FACTOR : 1);
    	return 0; // This is here so it is not mad
    }
    
    public double getRightAxis() {
    	//return (reverseDrive ? -(getHID(gamepad1).axis(driveLeftAxis)) : (getHID(gamepad1).axis(driveRightAxis))) *
    			//(reduceSpeed ? Calibration.DRIVE_TRAIN_REDUCTION_FACTOR : 1);
    	return 0; // this is also here so that it is not mad
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
