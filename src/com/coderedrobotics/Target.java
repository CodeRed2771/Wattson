package com.coderedrobotics;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Servo;

public class Target {
	Thread visionThread;
	Relay lightRing;
	Servo cameraServo;
	
	// These are the variables that will hold the X and Y values of the two
	// registered images
	double centerX1 = 0.0;
	double centerX2 = 0.0;
	double centerY1 = 0.0;
	double centerY2 = 0.0;
	double height1 = 0.0;
	double height2 = 0.0;

	// These are going to be the location in between the two images
	double gearX = 0.0;
	double gearY = 0.0;

	// variables used to set the image resolution
	int resolutionX = 640;
	int resolutionY = 480;
	
	UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();

	int objectsFound = 0;
	private final Object imgLock = new Object();

	public Target() {
		lightRing = new Relay(Wiring.LIGHT_RING_RELAY);
		cameraServo = new Servo(Wiring.CAMERA_SERVO);
		camera.setResolution(resolutionX, resolutionY);

		visionThread = new VisionThread(camera, new GripPipeline(), gp -> {
			//SmartDashboard.putNumber("timer", System.currentTimeMillis());

			synchronized (imgLock) {
				
				objectsFound = gp.filterContoursOutput().size();
				
				if (objectsFound == 2) {

					Rect r1 = Imgproc.boundingRect(gp.filterContoursOutput().get(0));
					Rect r2 = Imgproc.boundingRect(gp.filterContoursOutput().get(1));

					// This calculates the the centerpoint of each piece of tape
					centerX1 = r1.x + (r1.width / 2);
					centerX2 = r2.x + (r2.width / 2);
					centerY1 = r1.y + (r1.height / 2);
					centerY2 = r2.y + (r2.height / 2);
					height1 = r1.height;
					height2 = r2.height;

					// This calculates the X and Y of the space between the
					// pieces of tape
					gearX = (centerX1 + centerX2) / 2;
					gearY = (centerY1 + centerY2) / 2;

				} else {

					gearX = -1;
					gearY = -1;
				}
				
			}

		}
		);

		visionThread.setDaemon(true);
		visionThread.start();
	}

	public double degreesOffTarget() {
		return ((resolutionX / 2) - gearX) / 10;
	}
	
	public boolean isOnTarget() {
		return (Math.abs(degreesOffTarget()) <= 10);
	}

	public boolean foundTarget() {
		return (gearX >= 0 && gearY >= 0);
	}
	
	
	// servo position values need to be tested
	public void gearPickupView() {
		cameraServo.set(0);
	}
	
	public void gearTargetView() {
		cameraServo.set(0.25);
	}
	
	public void boilerView() {
		cameraServo.set(1);
	}
	
	public void regularView() {
		cameraServo.set(0.5);
	}
	
	public void gearReceiverView(){
		cameraServo.set(.75);
	}

	public double distanceFromGearTarget() {
		//k(constant) = d (distance) * h (average pixel height)
		//3300 is the constant
		return 3300 / ((height1+height2)/2);
	}
	
	public void setTargetingExposure(boolean darkFlag) {
		if (darkFlag)
			camera.setExposureManual(10);
		else
			camera.setExposureManual(50);		
	}
	
	public void enableVisionTargetMode(boolean enableFlag) {
		setTargetingExposure(enableFlag);
		enableLightRing(enableFlag);
	}

	public void enableLightRing(boolean turnOn) {
		if (turnOn) 
			lightRing.set(Value.kOn);
		else
			lightRing.set(Value.kOff);
	}
	
	public void displayDetails() {
		// puts the numbers of the final X and Y on the dashboard
		SmartDashboard.putNumber("Center X", gearX);
		SmartDashboard.putNumber("Center Y", gearY);
		SmartDashboard.putNumber("Objects Found", objectsFound);
		SmartDashboard.putNumber("rectangle one height", height1);
		SmartDashboard.putNumber("rectangle two height", height2);
		SmartDashboard.putNumber("Distance From Target", distanceFromGearTarget());
		SmartDashboard.putNumber("degrees off target", degreesOffTarget());
	}
}