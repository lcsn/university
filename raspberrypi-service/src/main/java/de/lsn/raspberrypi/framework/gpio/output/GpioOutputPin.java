package de.lsn.raspberrypi.framework.gpio.output;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.impl.GpioPinImpl;

public class GpioDigitalOutputPin extends GpioPinImpl {

	private GpioPinDigitalOutput gpioPin;
	private boolean active = true;

	public GpioDigitalOutputPin(GpioController gpio, Pin raspiPin) {
		this(gpio, null, raspiPin);
	}
	
	public GpioDigitalOutputPin(GpioController gpio, GpioProvider provider, Pin pin) {
		super(gpio, provider, pin);
		this.gpioPin = gpio.provisionDigitalOutputPin(pin);
		init();
	}
	
	private void init() {
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public GpioPinDigitalOutput getGpioPin() {
		return gpioPin;
	}
	
}
