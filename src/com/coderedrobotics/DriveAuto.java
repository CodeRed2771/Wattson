package com.coderedrobotics;

import com.coderedrobotics.libs.Logger;
import com.coderedrobotics.libs.PIDControllerAIAO;
import com.coderedrobotics.libs.PIDSourceFilter;
import com.coderedrobotics.libs.PWMController;
import com.coderedrobotics.libs.PWMSplitter2X;
import com.coderedrobotics.libs.TankDrive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDInterface;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveAuto {

	private PIDHolder leftPIDHolder;
	private PIDHolder rightPIDHolder;

    private PIDControllerAIAO leftDrivePID;
    private PIDControllerAIAO rightDrivePID;
    private Drive mainDrive;
    
    private boolean drivingStraight = true;
        
    public DriveAuto(Drive mainDrive) {
     	this.mainDrive = mainDrive;
     	leftPIDHolder = new PIDHolder();
     	rightPIDHolder = new PIDHolder();
     	
     	// was P = .003
        leftDrivePID = new PIDControllerAIAO(.0, 0, 0, new PIDSourceFilter((double value) -> -mainDrive.getLeftEncoderObject().get()), leftPIDHolder,  false, "autoleft");
        rightDrivePID = new PIDControllerAIAO(.0, 0, 0, new PIDSourceFilter((double value) -> -mainDrive.getRightEncoderObject().get()), rightPIDHolder, false, "autoright");
 
        leftDrivePID.setAbsoluteTolerance(.5); // half inch
        rightDrivePID.setAbsoluteTolerance(.5);
        leftDrivePID.setToleranceBuffer(10); // ten readings
        rightDrivePID.setToleranceBuffer(10);
        leftDrivePID.setSetpoint(0);
        leftDrivePID.reset();
        rightDrivePID.setSetpoint(0);
        rightDrivePID.reset();
        
    }
   
    public void driveInches(double d, double maxPower) {
    	rightDrivePID.setOutputRange(-maxPower, maxPower);
    	leftDrivePID.setOutputRange(-maxPower, maxPower);
    	
    	resetEncoders();
    	drivingStraight = true;

    	rightDrivePID.setSetpoint(-mainDrive.getRightEncoderObject().get() + convertToTicks(d));
    	leftDrivePID.setSetpoint(-mainDrive.getLeftEncoderObject().get() + convertToTicks(d));
    	
    	rightDrivePID.enable();
    	leftDrivePID.enable();
    }
    
    
    public void turnDegrees(double d, double maxPower) {
    	
    	double inchesToTravel = d/6;
  	
    	rightDrivePID.setOutputRange(-maxPower, maxPower);
    	leftDrivePID.setOutputRange(-maxPower, maxPower);

    	resetEncoders();
       	drivingStraight = false;
            	
   		leftDrivePID.setSetpoint(-mainDrive.getLeftEncoderObject().get() + convertToTicks(inchesToTravel));	
		rightDrivePID.setSetpoint(-mainDrive.getRightEncoderObject().get() + convertToTicks(-inchesToTravel));
      	
    	leftDrivePID.enable();
    	rightDrivePID.enable();
    }
    
    private double encoderAdjust() {
    	if (drivingStraight) 
    		return (mainDrive.getRightEncoderObject().get() - mainDrive.getLeftEncoderObject().get()) * .01;
    	else
    		return 0;
    }
    
    private void outputToDriveTrain() {
    	// this is called from the PIDWrites to send the new output values to the main drive object
    	mainDrive.set(leftPIDHolder.PIDvalue, rightPIDHolder.PIDvalue + encoderAdjust());
    }
    
    public void stop() {
    	leftDrivePID.disable();
    	rightDrivePID.disable();
    	leftDrivePID.setSetpoint(0); // added 3/18/16 DVV
    	rightDrivePID.setSetpoint(0); // added 3/18/16 DVV
    }
    
    public boolean hasArrived() {
    	return leftDrivePID.onTarget() && rightDrivePID.onTarget();
    }
    
    public void resetEncoders() {
    	mainDrive.getLeftEncoderObject().reset();
    	mainDrive.getRightEncoderObject().reset();
    }
    
    public void setPIDstate(boolean isEnabled) {
    	if (isEnabled) {
    		leftDrivePID.enable();
    		rightDrivePID.enable();
    	} else
    	{
    		leftDrivePID.disable();
    		rightDrivePID.disable();
    	}
    }
      
    private int convertToTicks(int inches) {
    	return (int)(inches * Calibration2016.DRIVE_DISTANCE_TICKS_PER_INCH);
    }
    private int convertToTicks(double inches) {
    	return (int)(inches * Calibration2016.DRIVE_DISTANCE_TICKS_PER_INCH);
    }
   
	private class PIDHolder implements PIDOutput {
		public double PIDvalue = 0;
		
		@Override
		public void pidWrite(double output) {
			PIDvalue = output;
			outputToDriveTrain();
		}
	}
	   
    public void showEncoderValues() {
    	//SmartDashboard.putNumber("Left Drive Encoder Distance: ", leftEncoder.getDistance());
    	//SmartDashboard.putNumber("Right Drive Encoder Distance: ", rightEncoder.getDistance());
    	//SmartDashboard.putNumber("Right PID error", rightDrivePID.getError());
     	SmartDashboard.putNumber("Left Drive PID Avg Error: ", leftDrivePID.getAvgError());
      	SmartDashboard.putNumber("Right Drive PID Avg Error: ", rightDrivePID.getAvgError());

      	SmartDashboard.putNumber("Left Drive Encoder Get: ", mainDrive.getLeftEncoderObject().get());
     	SmartDashboard.putNumber("Right Drive Encoder Get: ", mainDrive.getRightEncoderObject().get());
     	
//     	SmartDashboard.putNumber("Left Drive Distance: ", leftEncoder.getDistance());
//     	SmartDashboard.putNumber("Right Drive Distance: ", rightEncoder.getDistance());
      	
     	SmartDashboard.putNumber("Left Setpoint: ", leftDrivePID.getSetpoint());
      	SmartDashboard.putNumber("Right Setpoint: ", rightDrivePID.getSetpoint());
      	
     	SmartDashboard.putBoolean("Left On Target", leftDrivePID.onTarget());
      SmartDashboard.putBoolean("Right On Target", rightDrivePID.onTarget());
      	
     	//SmartDashboard.putNumber("Right Drive Encoder Raw: ", rightEncoder.getRaw());
       	   	
      	//SmartDashboard.putNumber("Right Setpoint: ", rightDrivePID.getSetpoint());
    }
    public void showPIDValues(){
    	//This will show the PID values of the driveAuto (so they can be used within other objects)
    	SmartDashboard.putNumber("P (DriveAuto): ", leftDrivePID.getP());
    	SmartDashboard.putNumber("I (DriveAuto): ", leftDrivePID.getI());
    	SmartDashboard.putNumber("D (DriveAuto): ", leftDrivePID.getD());
    	
    }
    public void updatePIDValues(){
    	//Changes the PID values to the values in SmartDashboard
    	//leftDrivePID.setPID(SmartDashboard.getNumber("P (DriveAuto): ",0), SmartDashboard.getNumber("I (DriveAuto): ",0), SmartDashboard.getNumber("D (DriveAuto): ",0));
    	//rightDrivePID.setPID(SmartDashboard.getNumber("P (DriveAuto): ",0),SmartDashboard.getNumber("I (DriveAuto): ",0),SmartDashboard.getNumber("D (DriveAuto): ",0));
    }
   
}
