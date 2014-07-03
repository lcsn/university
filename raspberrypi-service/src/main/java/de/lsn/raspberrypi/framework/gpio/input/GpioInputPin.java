package de.lsn.raspberrypi.framework.gpio.input;

import java.util.Vector;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.impl.GpioPinImpl;

public class GpioInputPin extends GpioPinImpl {

	private GpioPinDigitalInput gpioPin;
	private boolean active = true;
	private PinState state;

	private Vector<GpioInputListener> gpioDigitalInputListeners = new Vector<GpioInputListener>();
	
	public GpioInputPin(GpioController gpio, Pin raspiPin) {
		this(gpio, GpioFactory.getDefaultProvider(), raspiPin);
	}
	
	public GpioInputPin(GpioController gpio, GpioProvider provider, Pin pin) {
		super(gpio, provider, pin);
		this.gpioPin = gpio.provisionDigitalInputPin(pin, PinPullResistance.PULL_DOWN);
		this.state = gpioPin.getState();
		init();
	}
	
	private void init() {
		gpioPin.addListener(new GpioPinListenerDigital() {

			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if (active && !gpioPin.isState(state)) {
					fireOnInputChanged(event.getPin(), event.getState());
					state = event.getState();
				}
			}
		});		
	}

	private void fireOnInputChanged(GpioPin pin, PinState state) {
		for (GpioInputListener listener: gpioDigitalInputListeners) {
			listener.onInputChanged(new GpioInputEvent(pin, state));
		} 
	}
	
	public void addGpioDigitalInputListener(GpioInputListener listener) {
		gpioDigitalInputListeners.add(listener);
	}
	
	public void removeGpioDigitalInputListener(GpioInputListener listener) {
		gpioDigitalInputListeners.remove(listener);
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public GpioPinDigitalInput getGpioPin() {
		return gpioPin;
	}
	
}
