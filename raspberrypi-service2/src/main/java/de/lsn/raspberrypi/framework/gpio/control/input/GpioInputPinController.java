package de.lsn.raspberrypi.framework.gpio.control.input;

import java.util.concurrent.ConcurrentHashMap;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.Pin;

import de.lsn.raspberrypi.framework.gpio.event.GpioInputEvent;
import de.lsn.raspberrypi.framework.gpio.listener.GpioInputListener;
import de.lsn.raspberrypi.framework.gpio.pin.IGpioPin;
import de.lsn.raspberrypi.framework.gpio.pin.input.GpioInputPin;

public class GpioInputPinController {

	private ConcurrentHashMap<Pin, GpioInputPin> gpioInputPinMap = new ConcurrentHashMap<Pin, GpioInputPin>();
	private static GpioInputPinController instance;

	public GpioInputPinController() {
	}
	
	public static GpioInputPinController getInstance() {
		if (null == instance) {
			instance = new GpioInputPinController();
		}
		return instance;
	}
	
	public IGpioPin create(GpioController gpio, Pin pin) {
		GpioInputPin digitalInputPin;
		if (isNew(pin)) {
			digitalInputPin = new GpioInputPin(gpio, pin);
			digitalInputPin.addGpioDigitalInputListener(new GpioInputListener() {
				
				@Override
				public void onInputChanged(GpioInputEvent event) {
					System.out.println("Pin "+event.getPin()+" state changed to "+event.getState());
				}
			});
			gpioInputPinMap.put(pin, digitalInputPin);
		}
		else {
			digitalInputPin = gpioInputPinMap.get(pin);
		}
		return digitalInputPin;
	}
	
	public void restart(Pin pin) {
		if (!isNew(pin)) {
			GpioInputPin digitalInputPin = gpioInputPinMap.get(pin);
			digitalInputPin.setActive(true);
		}
	}
	
	public void stop(Pin pin) {
		if (!isNew(pin)) {
			GpioInputPin inputPin = gpioInputPinMap.get(pin);
			inputPin.setActive(false);
		}
	}
	
	public void unexport(Pin pin) {
		stop(pin);
		gpioInputPinMap.remove(pin).getPin();
	}
	
//	public void stopAll() {
//		if (null != gpioInputPinMap && gpioInputPinMap.size() > 0) {
//			for (GpioInputPin gpioPin : gpioInputPinMap.values()) {
//				stop(gpioPin.getPin());
//			}
//		}
//	}
	
	public boolean isNew(Pin pin) {
		return !gpioInputPinMap.containsKey(pin);
	}
	
}
