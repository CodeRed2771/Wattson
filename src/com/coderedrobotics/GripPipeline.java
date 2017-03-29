package com.coderedrobotics;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionPipeline;

import org.opencv.core.*;
import org.opencv.imgproc.*;

/**
 * GripPipeline class.
 *
 * <p>
 * An OpenCV pipeline generated by GRIP.
 *
 * @author GRIP
 */
public class GripPipeline implements VisionPipeline {
	private int HUE_LOW = 55;
	private int HUE_HIGH = 85;
	private int SAT_LOW = 60;
	private int SAT_HIGH = 255;
	private int LUM_LOW = 40;
	private int LUM_HIGH = 255;
	
	NetworkTable netTable;
//	CvSource outputRawStream = CameraServer.getInstance().putVideo("TargetInfoRaw", 640, 480);
	CvSource outputFilteredStream = CameraServer.getInstance().putVideo("TargetInfoFiltered", 320, 240);

	// Outputs
	private Mat hslThresholdOutput = new Mat();
	private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
	private ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<MatOfPoint>();

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public GripPipeline() {
		netTable = NetworkTable.getTable("Vision Grip");

		SmartDashboard.putNumber("Hue Low", HUE_LOW);
		SmartDashboard.putNumber("Hue High", HUE_HIGH);
		SmartDashboard.putNumber("Saturation Low", SAT_LOW);
		SmartDashboard.putNumber("Saturation High", SAT_HIGH);
		SmartDashboard.putNumber("Lum Low", LUM_LOW);
		SmartDashboard.putNumber("Lum High", LUM_HIGH);
	}

	/**
	 * This is the primary method that runs the entire pipeline and updates the
	 * outputs.
	 */
	@Override
	public void process(Mat source0) {
		//SmartDashboard.putNumber("GripPipeline systemtime", System.currentTimeMillis());

		// Step RGB_Threshold0:
		Mat hslThresholdInput = source0;
		Mat origMat = new Mat();
		hslThresholdInput.copyTo(origMat);

		double[] hslThresholdHue = {SmartDashboard.getNumber("Hue Low", HUE_LOW), SmartDashboard.getNumber("Hue High", HUE_HIGH)};
		double[] hslThresholdSaturation = {SmartDashboard.getNumber("Saturation Low", SAT_LOW), SmartDashboard.getNumber("Saturation High", SAT_HIGH)};
		double[] hslThresholdLuminance = {SmartDashboard.getNumber("Lum Low", LUM_LOW), SmartDashboard.getNumber("Lum High", LUM_HIGH)};
		
//		double[] hslThresholdHue = {HUE_LOW, HUE_HIGH};
//		double[] hslThresholdSaturation = {SAT_LOW, SAT_HIGH};
//		double[] hslThresholdLuminance = {LUM_LOW, LUM_HIGH};
		hslThreshold(hslThresholdInput, hslThresholdHue, hslThresholdSaturation, hslThresholdLuminance, hslThresholdOutput);

		// Step Find_Contours0:
		Mat findContoursInput = hslThresholdOutput;
	  	//outputRawStream.putFrame(findContoursInput);
		
		boolean findContoursExternalOnly = false;
		findContours(findContoursInput, findContoursExternalOnly, findContoursOutput);

		netTable.putNumber("Contours Found Unfiltered", findContoursOutput.size());

		// Step Filter_Contours0:
		ArrayList<MatOfPoint> filterContoursContours = findContoursOutput;
		double filterContoursMinArea = 20.0;
		double filterContoursMinPerimeter = 0.0;
		double filterContoursMinWidth = 6.0;
		double filterContoursMaxWidth = findContoursInput.width() / 10;
		double filterContoursMinHeight = findContoursInput.height() / 50;
		double filterContoursMaxHeight = findContoursInput.height() / 3;
		double[] filterContoursSolidity = { 0.0, 100.0 };
		double filterContoursMaxVertices = 1000000.0;
		double filterContoursMinVertices = 0.0;
		double filterContoursMinRatio = 0.3;
		double filterContoursMaxRatio = 0.7;
		filterContours(filterContoursContours, filterContoursMinArea, filterContoursMinPerimeter,
				filterContoursMinWidth, filterContoursMaxWidth, filterContoursMinHeight, filterContoursMaxHeight,
				filterContoursSolidity, filterContoursMaxVertices, filterContoursMinVertices, filterContoursMinRatio,
				filterContoursMaxRatio, filterContoursOutput);

		netTable.putNumber("Contours Found Filtered", filterContoursOutput.size());

		// display rectangles around the filtered list of items found
		addRectangles(origMat, filterContoursOutput);
		
		outputFilteredStream.putFrame(origMat);
		
		origMat.release();
	}

	private void addRectangles(Mat image, List<MatOfPoint> contours) {
		for (int i = 0; i < contours.size(); i++) {
			final MatOfPoint contour = contours.get(i);
			final Rect bb = Imgproc.boundingRect(contour);

			Imgproc.rectangle(image, new Point(bb.x, bb.y), new Point(bb.x+bb.width, bb.y+bb.height), new Scalar(255, 255, 255), 2);
		}
	}
	
	public Mat hslThresholdOutput() {
		return hslThresholdOutput;
	}

	public ArrayList<MatOfPoint> findContoursOutput() {
		return findContoursOutput;
	}

	public ArrayList<MatOfPoint> filterContoursOutput() {
		return filterContoursOutput;
	}

	/**
	 * Segment an image based on hue, saturation, and luminance ranges.
	 *
	 * @param input The image on which to perform the HSL threshold.
	 * @param hue The min and max hue
	 * @param sat The min and max saturation
	 * @param lum The min and max luminance
	 * @param output The image in which to store the output.
	 */
	private void hslThreshold(Mat input, double[] hue, double[] sat, double[] lum,
		Mat out) {
		Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HLS);
		Core.inRange(out, new Scalar(hue[0], lum[0], sat[0]),
			new Scalar(hue[1], lum[1], sat[1]), out);
	}

	/**
	 * Sets the values of pixels in a binary image to their distance to the
	 * nearest black pixel.
	 * 
	 * @param input
	 *            The image on which to perform the Distance Transform.
	 * @param type
	 *            The Transform.
	 * @param maskSize
	 *            the size of the mask.
	 * @param output
	 *            The image in which to store the output.
	 */
	private void findContours(Mat input, boolean externalOnly, List<MatOfPoint> contours) {
		Mat hierarchy = new Mat();
		contours.clear();
		int mode;
		if (externalOnly) {
			mode = Imgproc.RETR_EXTERNAL;
		} else {
			mode = Imgproc.RETR_LIST;
		}
		int method = Imgproc.CHAIN_APPROX_SIMPLE;
		Imgproc.findContours(input, contours, hierarchy, mode, method);
	}

	/**
	 * Filters out contours that do not meet certain criteria.
	 * 
	 * @param inputContours
	 *            is the input list of contours
	 * @param output
	 *            is the the output list of contours
	 * @param minArea
	 *            is the minimum area of a contour that will be kept
	 * @param minPerimeter
	 *            is the minimum perimeter of a contour that will be kept
	 * @param minWidth
	 *            minimum width of a contour
	 * @param maxWidth
	 *            maximum width
	 * @param minHeight
	 *            minimum height
	 * @param maxHeight
	 *            maximimum height
	 * @param Solidity
	 *            the minimum and maximum solidity of a contour
	 * @param minVertexCount
	 *            minimum vertex Count of the contours
	 * @param maxVertexCount
	 *            maximum vertex Count
	 * @param minRatio
	 *            minimum ratio of width to height
	 * @param maxRatio
	 *            maximum ratio of width to height
	 */
	private void filterContours(List<MatOfPoint> inputContours, double minArea, double minPerimeter, double minWidth,
			double maxWidth, double minHeight, double maxHeight, double[] solidity, double maxVertexCount,
			double minVertexCount, double minRatio, double maxRatio, List<MatOfPoint> output) {
		
		final MatOfInt hull = new MatOfInt();
		
		output.clear();
		
		// operation
		for (int i = 0; i < inputContours.size(); i++) {
			final MatOfPoint contour = inputContours.get(i);
			final Rect bb = Imgproc.boundingRect(contour);
			if (bb.width < minWidth || bb.width > maxWidth)
				continue;
			if (bb.height < minHeight || bb.height > maxHeight)
				continue;
			final double area = Imgproc.contourArea(contour);
			if (area < minArea)
				continue;
			if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < minPerimeter)
				continue;
			Imgproc.convexHull(contour, hull);
			MatOfPoint mopHull = new MatOfPoint();
			mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
			for (int j = 0; j < hull.size().height; j++) {
				int index = (int) hull.get(j, 0)[0];
				double[] point = new double[] { contour.get(index, 0)[0], contour.get(index, 0)[1] };
				mopHull.put(j, 0, point);
			}
			final double solid = 100 * area / Imgproc.contourArea(mopHull);
			if (solid < solidity[0] || solid > solidity[1])
				continue;
			if (contour.rows() < minVertexCount || contour.rows() > maxVertexCount)
				continue;
			final double ratio = bb.width / (double) bb.height;
			if (ratio < minRatio || ratio > maxRatio)
				continue;
			
			output.add(contour);
		}
	}
}
