/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderedrobotics.libs;

import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author austin
 */
public class PIDDrive extends Drive {

    private Drive outDrive;
    private boolean enablePID = true;
    private double xmax, ymax, rotmax;
    public PIDControllerAIAO xPID, yPID, rotPID;

    public PIDDrive(Drive outDrive,
            PIDSource xSource, PIDSource ySource, PIDSource rotSource,
            double xp, double xi, double xd,
            double yp, double yi, double yd,
            double rotp, double roti, double rotd,
            double xmax, double ymax, double rotmax,
            double xf, double yf, double rotf, boolean useNetwork) {

        this.xmax = xmax;
        this.ymax = ymax;
        this.rotmax = rotmax;
        
        this.outDrive = outDrive;
        xPID = new PIDControllerAIAO(xp, xi, xd, xf, xSource, outDrive.getXPIDOutput(), useNetwork, "X drive");
        yPID = new PIDControllerAIAO(yp, yi, yd, yf, ySource, outDrive.getYPIDOutput(), useNetwork, "Y drive");
        rotPID = new PIDControllerAIAO(rotp, roti, rotd, rotf, rotSource, outDrive.getRotPIDOutput(), useNetwork, "rot drive");

        xPID.enable();
        yPID.enable();
        rotPID.enable();
        
        xPID.setInputRange(-xmax, xmax);
        yPID.setInputRange(-ymax, ymax);
        rotPID.setInputRange(-rotmax, rotmax);
    }

    public void enablePID() {
        enablePID = true;
        xPID.enable();
        yPID.enable();
        rotPID.enable();
    }

    public void disablePID() {
        enablePID = false;
        xPID.disable();
        yPID.disable();
        rotPID.disable();
    }

    private static double clip(double x) {
        return Math.min(1, Math.max(-1, x));
    }

    @Override
    protected void recalulate(double x, double y, double rot) {
        xPID.setSetpoint(clip(x) * xmax);
        yPID.setSetpoint(clip(y) * ymax);
        rotPID.setSetpoint(clip(rot) * rotmax);
    }
}
