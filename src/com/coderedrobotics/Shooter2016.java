package com.coderedrobotics;

import com.coderedrobotics.libs.Logger;
import com.coderedrobotics.libs.PIDControllerAIAO;
import com.coderedrobotics.libs.PWMController;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class Shooter {

    private final CANTalon shooter1;
    private final PWMController shooter2;
    private final PIDControllerAIAO pid;
    private final AutoStop autoStop;
    private boolean stopping;
    private double smoothedOutput = 0;
    private long timeout;
    private boolean hasBeenSpunUp = false;

    public Shooter(int talon, int victor) {
        shooter1 = new CANTalon(talon);
        shooter2 = new PWMController(victor, false);
        pid = new PIDControllerAIAO(Calibration.SHOOTER_P, Calibration.SHOOTER_I,
                Calibration.SHOOTER_D, Calibration.SHOOTER_F, new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {
            }

            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kDisplacement;
            }

            @Override
            public double pidGet() {
                return shooter1.getEncVelocity();
            }
        }, (double output) -> {
                    smoothedOutput = ((output * 0.2) + (smoothedOutput * 0.8));
                    shooter1.set(smoothedOutput);
                    shooter2.set(smoothedOutput);
                }, false, "Shooter");
        pid.setOutputRange(0, 1);
        autoStop = new AutoStop();
    }

    
    
    
    
    
    // OLD METHODS
    
    
    public boolean isSpunUp() {
        boolean spunUp = Math.abs(pid.getError()) < Calibration.SHOOTER_ERROR_TOLERANCE;
        hasBeenSpunUp = spunUp || hasBeenSpunUp;
        return spunUp;
    }

    public boolean hasBeenSpunUp() {
        isSpunUp(); // update hasBeenSpunUp
        return hasBeenSpunUp;
    }

    public void spinUp() {
        pid.setSetpoint(Calibration.SHOOTER_SPIN_SPEED);
        pid.enable();
    }

    public void stop() {
        smoothedOutput = 0;
        pid.setSetpoint(0);
        pid.disable();        
        autoStop.reset();
        hasBeenSpunUp = false;
    }

    public void stopWithDelay() {
        stopping = true;
        timeout = System.currentTimeMillis() + Calibration.SHOOTER_STOP_TIMEOUT;
    }

    public boolean hasFired() {
        return autoStop.tick();
    }

    public void tick() {

        if (stopping && System.currentTimeMillis() > timeout) {
            stop();
            stopping = false;
        }
    }
    
    public void enableOverrideMode() {
        
    }

    private class AutoStop {

        private boolean hasFired = false;
        private long time = -1;

        boolean tick() {
            if (hasBeenSpunUp && Math.abs(pid.getError()) > Calibration.SHOOTER_BALL_ERROR_THRESHOLD && time == -1) {
                hasFired = true;
                time = System.currentTimeMillis();
            }
           
            return hasFired && System.currentTimeMillis() > Calibration.SHOOTER_AUTOSTOP_DELAY + time;
        }

        void reset() {
            hasFired = false;
            time = -1;
        }
    }
}
