package de.lsn.raspberrypi.framework.gpio.event;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinState;

public class GpioInputEvent {

	private GpioPin pin;
	private PinState state;

	public GpioInputEvent(GpioPin pin, PinState state) {
		this.pin = pin;
		this.state = state;
	}
	
	public GpioPin getPin() {
		return pin;
	}
	
	public PinState getState() {
		return state;
	}
	
}
