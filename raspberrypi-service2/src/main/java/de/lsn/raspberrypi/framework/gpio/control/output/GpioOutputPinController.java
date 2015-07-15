package de.lsn.raspberrypi.framework.gpio.control.output;

import java.util.concurrent.ConcurrentHashMap;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;

import de.lsn.raspberrypi.framework.gpio.pin.output.GpioOutputPin;

public class GpioOutputPinController {

	private ConcurrentHashMap<Pin, GpioOutputPin> gpioOutputPinMap = new ConcurrentHashMap<Pin, GpioOutputPin>();
	private static GpioOutputPinController instance;

	public GpioOutputPinController() {
	}
	
	public static GpioOutputPinController getInstance() {
		if (null == instance) {
			instance = new GpioOutputPinController();
		}
		return instance;
	}
	
	public GpioPin create(GpioController gpio, Pin pin) {
		GpioOutputPin digitalInputPin;
		if (isNew(pin)) {
			digitalInputPin = new GpioOutputPin(gpio, pin);
			gpioOutputPinMap.put(pin, digitalInputPin);
		}
		else {
			digitalInputPin = gpioOutputPinMap.get(pin);
		}
		return digitalInputPin;
	}
	
	public void restart(Pin pin) {
		if (!isNew(pin)) {
			GpioOutputPin digitalInputPin = gpioOutputPinMap.get(pin);
			digitalInputPin.setActive(true);
		}
	}
	
	public void stop(Pin pin) {
		if (!isNew(pin)) {
			GpioOutputPin inputPin = gpioOutputPinMap.get(pin);
			inputPin.setActive(false);
		}
	}
	
	public boolean isNew(Pin pin) {
		return !gpioOutputPinMap.containsKey(pin);
	}
	
}
