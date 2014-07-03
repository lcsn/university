package de.lsn.raspberrypi.framework.gpio.input;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinState;

public class GpioDigitalInputEvent {

	private GpioPin pin;
	private PinState state;

	public GpioDigitalInputEvent(GpioPin pin, PinState state) {
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
