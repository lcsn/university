package de.lsn.raspberrypi.framework.gpio.control;

import java.util.concurrent.ConcurrentHashMap;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;

import de.lsn.raspberrypi.framework.gpio.output.GpioDigitalOutputPin;

public class GpioDigitalOutputPinController {

	private ConcurrentHashMap<Pin, GpioDigitalOutputPin> gpioOutputPinMap = new ConcurrentHashMap<Pin, GpioDigitalOutputPin>();
	private static GpioDigitalOutputPinController instance;

	public GpioDigitalOutputPinController() {
	}
	
	public static GpioDigitalOutputPinController getInstance() {
		if (null == instance) {
			instance = new GpioDigitalOutputPinController();
		}
		return instance;
	}
	
	public GpioPin create(GpioController gpio, Pin pin) {
		GpioDigitalOutputPin digitalInputPin;
		if (isNew(pin)) {
			digitalInputPin = new GpioDigitalOutputPin(gpio, pin);
			gpioOutputPinMap.put(pin, digitalInputPin);
		}
		else {
			digitalInputPin = gpioOutputPinMap.get(pin);
		}
		return digitalInputPin;
	}
	
	public void restart(Pin pin) {
		if (!isNew(pin)) {
			GpioDigitalOutputPin digitalInputPin = gpioOutputPinMap.get(pin);
			digitalInputPin.setActive(true);
		}
	}
	
	public void stop(Pin pin) {
		if (!isNew(pin)) {
			GpioDigitalOutputPin inputPin = gpioOutputPinMap.get(pin);
			inputPin.setActive(false);
		}
	}
	
	public boolean isNew(Pin pin) {
		return !gpioOutputPinMap.containsKey(pin);
	}
	
}
