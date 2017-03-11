package com.coderedrobotics;

import com.coderedrobotics.libs.PIDControllerAIAO;
import com.coderedrobotics.libs.PIDDerivativeCalculator;
import com.coderedrobotics.libs.PIDSourceFilter;
import com.coderedrobotics.libs.PWMSplitter2X;
import com.coderedrobotics.libs.TankDrive;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {

    private final TankDrive tankDrive;

    private Encoder leftEncoder;
    private Encoder rightEncoder;

    private PIDControllerAIAO drivePid;
    private PIDControllerAIAO rotPid;
    private PIDControllerAIAO driveDistPID;

    private final PWMSplitter2X leftPwmSplitter2X;
    private final PWMSplitter2X rightPwmSplitter2X;

    private boolean encoderError = false;
    private boolean disablePID = false;

    public Drive() {
        leftEncoder = new Encoder(Wiring.LEFT_ENCODER_A, Wiring.LEFT_ENCODER_B);
        rightEncoder = new Encoder(Wiring.RIGHT_ENCODER_A, Wiring.RIGHT_ENCODER_B);

        tankDrive = new TankDrive(
                leftPwmSplitter2X = new PWMSplitter2X(
                        Wiring.LEFT_DRIVE_MOTOR1,
                        Wiring.LEFT_DRIVE_MOTOR2,
                        false, leftEncoder),
                rightPwmSplitter2X = new PWMSplitter2X(
                        Wiring.RIGHT_DRIVE_MOTOR1,
                        Wiring.RIGHT_DRIVE_MOTOR2,
                        true, rightEncoder));

        drivePid = new PIDControllerAIAO(0, 0, 0, new PIDSourceFilter(
                new PIDDerivativeCalculator(
                        new PIDSourceFilter((double value) -> leftEncoder.getRaw() + rightEncoder.getRaw()), 10),
                (double value) -> value / Calibration.DRIVE_TOP_SPEED), tankDrive.getYPIDOutput(), false, "drive");

        rotPid = new PIDControllerAIAO(0, 0, 0, new PIDSourceFilter(
                new PIDDerivativeCalculator(
                        new PIDSourceFilter((double value) -> leftEncoder.getRaw() - rightEncoder.getRaw()), 10),
                (double value) -> value / Calibration.ROT_TOP_SPEED), tankDrive.getRotPIDOutput(), false, "rot");

        setPIDstate(false);

        //SmartDashboard.putNumber("DRIVE ROT P",Calibration.ROT_P);
        //SmartDashboard.putNumber("DRIVE ROT I", Calibration.ROT_I);
        //SmartDashboard.putNumber("DRIVE ROT D", Calibration.ROT_D);
        //SmartDashboard.putNumber("DRIVE P", Calibration.DRIVE_P);
        //SmartDashboard.putNumber("DRIVE I", Calibration.DRIVE_I);
        //SmartDashboard.putNumber("DRIVE D", Calibration.DRIVE_D);

    }

    public void setPIDstate(boolean isEnabled) {
    	if (isEnabled) {
    		drivePid.enable();
    		rotPid.enable();
    	} else
    	{
    		drivePid.disable();
     		rotPid.disable();
    	}
    }

    public boolean encoderHasError() {
        return leftEncoderHasError() || rightEncoderHasError();
    }

    public boolean leftEncoderHasError() {
        return leftPwmSplitter2X.getPWMControllerA().encoderHasError();
    }

    public boolean rightEncoderHasError() {
        return rightPwmSplitter2X.getPWMControllerA().encoderHasError();
    }

    public void setSquared(double left, double right) {
        set(Math.abs(left) * left, Math.abs(right) * right);
    }

    public Encoder getLeftEncoderObject() {
    	return leftEncoder;
    }
    public Encoder getRightEncoderObject() {
    	return rightEncoder;
    }
    public PWMSplitter2X getLeftPWM() {
    	return leftPwmSplitter2X;
    }
    public PWMSplitter2X getRightPWM() {
    	return rightPwmSplitter2X;
    }

    public void set(double left, double right) {
        if (encoderError = (rightPwmSplitter2X.getPWMControllerA().encoderHasError()
                || leftPwmSplitter2X.getPWMControllerA().encoderHasError()) || disablePID) {
            drivePid.setPID(0, 0, 0, 1);
            rotPid.setPID(0, 0, 0, 1);
        } else {

//        	rotPid.setPID(SmartDashboard.getNumber("DRIVE ROT P",Calibration.ROT_P), SmartDashboard.getNumber("DRIVE ROT I", Calibration.ROT_I), SmartDashboard.getNumber("DRIVE ROT D", Calibration.ROT_D));
//        	drivePid.setPID(SmartDashboard.getNumber("DRIVE P", Calibration.DRIVE_P), SmartDashboard.getNumber("DRIVE I", Calibration.DRIVE_I), SmartDashboard.getNumber("DRIVE D", Calibration.DRIVE_D));


        	drivePid.setPID(Calibration.DRIVE_P, Calibration.DRIVE_I, Calibration.DRIVE_D, 1);
          rotPid.setPID(Calibration.ROT_P, Calibration.ROT_I, Calibration.ROT_D, 1);
        }

        double rot = (left - right) / 2;

        drivePid.setSetpoint((left + right) / 2);
        rotPid.setSetpoint(Math.abs(Math.pow(Math.abs(rot), (1 - Math.abs((left + right) / 2)) * 0.9)) * rot);
//        rotPid.setSetpoint(rot);
    }

    public void autoSetDrive(double speed) {
    	drivePid.setPID(0, 0, 0, 1);
        drivePid.setSetpoint(speed);
    }

    public void autoSetRot(double rot) {
        rotPid.setPID(0, 0, 0, 1);
        rotPid.setSetpoint(rot);
    }

    public void disablePID() {
        disablePID = true;
    }

    public void tick() {
//    	SmartDashboard.putNumber("Drive Left Encoder", leftEncoder.get());
//    	SmartDashboard.putNumber("Drive Right Encoder", rightEncoder.get());
    }

}
