package de.lsn.raspberrypi.logic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigital;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import de.lsn.raspberrypi.framework.GpioException;
import de.lsn.raspberrypi.framework.pwm.GpioPwmPin;
import de.lsn.raspberrypi.framework.pwm.GpioPwmPinController;
import de.lsn.raspberrypi.framework.pwm.GpioPwmValueProvider;

@Singleton
public class GpioHelper {

	protected GpioController gpio;
	
	protected ConcurrentHashMap<Integer, Field> pinFieldMap = new ConcurrentHashMap<Integer, Field>();
	protected ConcurrentHashMap<String, PinMode> pinModeMap = new ConcurrentHashMap<String, PinMode>();
	
	protected ConcurrentHashMap<Integer, Pin> pinMap = new ConcurrentHashMap<Integer, Pin>();
	protected ConcurrentHashMap<Integer, GpioPin> gpioPinMap = new ConcurrentHashMap<Integer, GpioPin>();

	private GpioPinPwmOutput pwmPin;
	
	@PostConstruct
	private void init() {
		gpios();
		modes();
		GpioPwmValueProvider.getInstance();
	}

	private void gpios() {
		Field[] fields = RaspiPin.class.getDeclaredFields();
		for (Field f : fields) {
			if (Modifier.isStatic(f.getModifiers())) {
				if (StringUtils.startsWith(f.getName(), "GPIO_")) {
					Integer pinNo = Integer.valueOf(StringUtils.substringAfterLast(f.getName(), "_"));
					pinFieldMap.put(pinNo, f);
				}
			}
		}
	}

	private void modes() {
		for (PinMode pinMode : PinMode.values()) {
			pinModeMap.put(pinMode.getName(), pinMode);
		}
	}

	public void export(Integer pin, GpioPin gpioPin) {
		if (gpioPinMap.containsKey(pin)) {
			gpioPinMap.put(pin, gpioPin);
		}
	}
	
	public void unexport(Integer pin) {
		if (gpioPinMap.containsKey(pin)) {
			gpioPinMap.remove(pin);
//			GpioPin gpioPin = gpioPinMap.remove(pin);
//			if (pinMap.containsKey(pin)) {
				pinMap.remove(pin);
//				gpioPin.getProvider().unexport(pin);
//			}
		}
	}
	
	public void unexportAll() {
		for (Integer pin : gpioPinMap.keySet()) {
			unexport(pin);
		}
	}
	
	public GpioPin toGpioPin(PinDirection direction, Integer pin) throws GpioException {
		GpioPin gpioPin = null;
		Pin raspiPin = toRaspiPin(pin);
		if (null != raspiPin && gpioPinMap.containsKey(pin)) {
			gpioPin = gpioPinMap.get(pin);
		}
		else {
			switch (direction) {
			case IN:
				gpioPin = gpio().provisionDigitalInputPin(raspiPin);
				break;
			case OUT:
				gpioPin = gpio().provisionDigitalOutputPin(raspiPin);
				break;
			}
			gpioPinMap.put(pin, gpioPin);
		}
		return gpioPin;
	}
	
	public GpioPin toGpioPin(Integer pin) throws GpioException {
		return toGpioPin(PinDirection.OUT, pin);
	}
	
	public Pin toRaspiPin(Integer pin) {
		Pin raspiPin = null;
		try {
			if (pinMap.containsKey(pin)) {
				raspiPin = pinMap.get(pin);
			} else {
				Field pinField = pinFieldMap.get(pin);
				pinField.setAccessible(true);
				raspiPin = (Pin) pinField.get(null);
				pinField.setAccessible(false);
				pinMap.put(pin, raspiPin);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return raspiPin;
	}

	public PinMode toPinMode(String modeAsString) throws GpioException {
		PinMode mode = null;
		if (pinModeMap.containsKey(modeAsString)) {
			mode = pinModeMap.get(modeAsString);
		} else {
			throw new GpioException(modeAsString + "could not be resolved to PinMode");
		}
		return mode;
	}

	public void gpio(GpioController gpio) {
		this.gpio = gpio;
	}
	
	public GpioController gpio() throws GpioException {
		if (null == gpio) {
			throw new GpioException("Gpio service not yet started. Use ../gpio/startup");
		}
		return gpio;
	}

	public void startUp() {
		gpio(GpioFactory.getInstance());		
	}
	
	public void shutdown() {
		gpio(null);
	}

	public PinState getState(GpioPin gpioPin) {
		return gpio.getState((GpioPinDigital) gpioPin);
	}

	public Collection<GpioPin> getExportedPins() {
		return gpioPinMap.values();
	}

	public GpioPinPwmOutput getPwmPin() throws GpioException {
		if (null == pwmPin) {
			pwmPin = gpio().provisionPwmOutputPin(RaspiPin.GPIO_01);
		}
		return pwmPin;
	}

	public GpioPwmPin newGpioPwmPin(final Integer pin) throws GpioException {
		Pin raspiPin = toRaspiPin(pin);
		GpioPwmPin gpioPwmPin;
		if (GpioPwmPinController.getInstance().isNew(raspiPin)) {
			gpioPwmPin = new GpioPwmPin(gpio(), raspiPin);
			GpioPwmPinController.getInstance().startNewPwm(gpioPwmPin);
		}
		else {
			gpioPwmPin = GpioPwmPinController.getInstance().restartPwm(raspiPin);
		}
		return gpioPwmPin;
	}
	
//	public Response removeGpioPwmPin(final Integer pin) {
//		Response response = Response.ok("GpioPwmPin "+pin+" was removed").build();
//		if (gpioPinMap.containsKey(pin)) {
//			gpioPinMap.remove(pin);
//		}
//		else {
//			response = Response.status(Status.NOT_FOUND).entity("Not GpioPwmPin on address "+toRaspiPin(pin).getAddress()+" provisioned").build();
//		}
//		return response;
//	}
	
}
