package com.coderedrobotics.libs;


/**
 *
 * @author michael
 */
public class VirtualizablePsuedoDigitalInput extends VirtualizableAnalogInput {

    public VirtualizablePsuedoDigitalInput(int port) {
        super(port);
    }

    public boolean getState() {
        return super.get() > 2;
    }
}
