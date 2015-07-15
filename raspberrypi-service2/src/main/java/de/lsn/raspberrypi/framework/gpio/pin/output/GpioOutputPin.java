package de.lsn.raspberrypi.framework.gpio.pin.output;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.impl.GpioPinImpl;

public class GpioOutputPin extends GpioPinImpl {

	private GpioPinDigitalOutput gpioPin;
	private boolean active = true;

	public GpioOutputPin(GpioController gpio, Pin raspiPin) {
		this(gpio, GpioFactory.getDefaultProvider(), raspiPin);
	}
	
	public GpioOutputPin(GpioController gpio, GpioProvider provider, Pin pin) {
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
