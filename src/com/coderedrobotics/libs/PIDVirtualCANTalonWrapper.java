package com.coderedrobotics.libs;

import com.ctre.CANTalon;

public class PIDVirtualCANTalonWrapper extends CANTalon implements Runnable {
	
	private boolean useNetwork = false;
	private String name;
	
	private double robotP;
	private double robotI;
	private double robotD;
	private double robotS;
	
	private Thread thread;
	
	public PIDVirtualCANTalonWrapper(int port) {
		this(port, null, false);
	}
	
	public PIDVirtualCANTalonWrapper(int port, String name, boolean useNetwork) {
		super(port);
		this.useNetwork = useNetwork;
		this.name = name;
		if (useNetwork) {
			PIDNetworkTuner.getInstance().addPIDController(name);
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@Override
	public void setPID(double p, double i, double d) {
		robotP = p;
		robotI = i;
		robotD = d;		
	}
	
	@Override
	public void setP(double p) {
		robotP = p;
	}
	
	@Override
	public void setI(double i) {
		robotI = i;
	}
	
	@Override
	public void setD(double d) {
		robotD = d;
	}
	
	@Override
	public void setSetpoint(double setpoint) {
		robotS = setpoint;
	}

	@Override
	public void run() {
		while(true) {
			if (useNetwork) {
	            PIDNetworkTuner tuner = PIDNetworkTuner.getInstance();
	            if (tuner.networkHasP(name)) {
	                super.setP(tuner.getP(name));
	            } else {
	                super.setP(robotP);
	            }
	            if (tuner.networkHasI(name)) {
	                super.setI(tuner.getI(name));
	            } else {
	                super.setI(robotI);
	            }
	            if (tuner.networkHasD(name)) {
	                super.setD(tuner.getD(name));
	            } else {
	                super.setD(robotD);
	            }
	            if (tuner.networkHasSetpoint(name)) {
	                super.setSetpoint(tuner.getSetpoint(name));
	            } else {
	                super.setSetpoint(robotS);
	            }
	            
	            tuner.update(name, super.getOutputVoltage(), super.getError(), robotP, robotI, robotD, super.getSetpoint());
	        } else {
	            super.setP(robotP);
	            super.setI(robotI);
	            super.setD(robotD);
	            super.setSetpoint(robotS);
	        }
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
