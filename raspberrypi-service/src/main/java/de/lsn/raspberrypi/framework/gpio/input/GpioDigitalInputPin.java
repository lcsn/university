package de.lsn.raspberrypi.framework.gpio.input;

import java.util.Vector;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.impl.GpioPinImpl;

public class GpioDigitalInputPin extends GpioPinImpl {

	private GpioPinDigitalInput gpioPin;
	private boolean active = true;

	private Vector<GpioDigitalInputListener> gpioDigitalInputListeners = new Vector<GpioDigitalInputListener>();
	
	public GpioDigitalInputPin(GpioController gpio, Pin raspiPin) {
		this(gpio, null, raspiPin);
	}
	
	public GpioDigitalInputPin(GpioController gpio, GpioProvider provider, Pin pin) {
		super(gpio, provider, pin);
		this.gpioPin = gpio.provisionDigitalInputPin(pin);
		init();
	}
	
	private void init() {
		gpioPin.addListener(new GpioPinListenerDigital() {

			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if (active) {
					System.out.println(" GPIO PIN STATE CHANGED: " + event.getPin() + " --> " + event.getState() + " EventType: " + event.getEventType());
					fireOnInputChanged(event.getPin(), event.getState());
				}
			}
		});		
	}

	private void fireOnInputChanged(GpioPin pin, PinState state) {
		for (GpioDigitalInputListener listener: gpioDigitalInputListeners) {
			listener.onInputChanged(new GpioDigitalInputEvent(pin, state));
		} 
	}
	
	public void addGpioDigitalInputListener(GpioDigitalInputListener listener) {
		gpioDigitalInputListeners.add(listener);
	}
	
	public void removeGpioDigitalInputListener(GpioDigitalInputListener listener) {
		gpioDigitalInputListeners.remove(listener);
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
	
}
