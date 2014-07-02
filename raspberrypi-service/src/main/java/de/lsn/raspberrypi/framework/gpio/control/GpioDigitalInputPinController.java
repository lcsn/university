package de.lsn.raspberrypi.framework.gpio.control;

import java.util.concurrent.ConcurrentHashMap;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;

import de.lsn.raspberrypi.framework.gpio.input.GpioDigitalInputPin;

public class GpioDigitalInputPinController {

	private ConcurrentHashMap<Pin, GpioDigitalInputPin> gpioInputPinMap = new ConcurrentHashMap<Pin, GpioDigitalInputPin>();
	private static GpioDigitalInputPinController instance;

	public GpioDigitalInputPinController() {
		//		NOP
	}
	
	public static GpioDigitalInputPinController getInstance() {
		if (null == instance) {
			instance = new GpioDigitalInputPinController();
		}
		return instance;
	}
	
	public GpioPin create(GpioController gpio, Pin pin) {
		GpioDigitalInputPin digitalInputPin;
		if (isNew(pin)) {
			digitalInputPin = new GpioDigitalInputPin(gpio, pin);
			gpioInputPinMap.put(pin, digitalInputPin);
		}
		else {
			digitalInputPin = gpioInputPinMap.get(pin);
		}
		return digitalInputPin;
	}
	
	public void restart(Pin pin) {
		if (!isNew(pin)) {
			GpioDigitalInputPin digitalInputPin = gpioInputPinMap.get(pin);
			digitalInputPin.setActive(true);
		}
	}
	
	public void stop(Pin pin) {
		if (!isNew(pin)) {
			GpioDigitalInputPin inputPin = gpioInputPinMap.get(pin);
			inputPin.setActive(false);
		}
	}
	
	public boolean isNew(Pin pin) {
		return !gpioInputPinMap.containsKey(pin);
	}
	
}
