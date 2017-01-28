package com.coderedrobotics.libs;

/**
 *
 * @author austin
 */
public class TankDrive extends Drive {

    private final SettableController lVictor, rVictor;

    public TankDrive(SettableController lVictor, SettableController rVictor) {
        this.lVictor = lVictor;
        this.rVictor = rVictor;
    }

    @Override
    protected void recalulate(double x, double y, double rot) {
        lVictor.set(y + rot);
        rVictor.set(y - rot);
    }
}
