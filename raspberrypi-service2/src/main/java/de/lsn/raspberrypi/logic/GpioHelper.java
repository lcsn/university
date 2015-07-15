package de.lsn.raspberrypi.logic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigital;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import de.lsn.raspberrypi.framework.GpioPwmConstants;
import de.lsn.raspberrypi.framework.gpio.control.input.GpioInputPinController;
import de.lsn.raspberrypi.framework.gpio.control.output.GpioPwmOutputPinController;
import de.lsn.raspberrypi.framework.gpio.exception.GpioException;
import de.lsn.raspberrypi.framework.gpio.pin.output.pwm.GpioPwmOutputPin;
import de.lsn.raspberrypi.framework.gpio.pin.output.pwm.GpioPwmValueProvider;

@Singleton
public class GpioHelper {

	protected GpioController gpio;
	
	protected ConcurrentHashMap<String, PinMode> pinModeMap = new ConcurrentHashMap<String, PinMode>();
	protected ConcurrentHashMap<Integer, Pin> raspiPinMap = new ConcurrentHashMap<Integer, Pin>();
	
	protected ConcurrentHashMap<Integer, GpioPin> gpioPinMap = new ConcurrentHashMap<Integer, GpioPin>();

	@PostConstruct
	private void init() {
		gpios();
		modes();
		GpioPwmValueProvider.getInstance().setGranularity(5);
		GpioPwmValueProvider.getInstance().provide();
	}

	private void gpios() {
		Field[] fields = RaspiPin.class.getDeclaredFields();
		for (Field f : fields) {
			if (Modifier.isStatic(f.getModifiers())) {
				if (StringUtils.startsWith(f.getName(), "GPIO_")) {
					try {
						Integer pinNo = Integer.valueOf(StringUtils.substringAfterLast(f.getName(), "_"));
						f.setAccessible(true);
						Pin raspiPin = (Pin) f.get(null);
						f.setAccessible(false);
						raspiPinMap.put(pinNo, raspiPin);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
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
			GpioPin gpioPin = gpioPinMap.remove(pin);
			gpio.unprovisionPin(gpioPin);
		}
		gpioPinMap.clear();
	}
	
	public void unexportAll() {
		for (Integer pin : gpioPinMap.keySet()) {
			unexport(pin);
		}
	}
	
	public GpioPin newGpioPin(Integer pin, PinDirection direction) throws GpioException {
		GpioPin gpioPin = null;
		Pin raspiPin = toRaspiPin(pin);
		if (null != raspiPin && gpioPinMap.containsKey(pin)) {
			gpioPin = gpioPinMap.get(pin);
		}
		else {
			switch (direction) {
			case IN:
//				gpioPin = gpio().provisionDigitalInputPin(raspiPin);
				gpioPin = GpioInputPinController.getInstance().create(gpio(), raspiPin);
				break;
			case OUT:
//				FIXME ALT - Muss analog zu case IN: aussehen.
				gpioPin = gpio().provisionDigitalOutputPin(raspiPin);
				break;
			}
			gpioPinMap.put(pin, gpioPin);
		}
		return gpioPin;
	}
	
	public GpioPin toGpioPin(Integer pin) throws GpioException {
		if (!gpioPinMap.containsKey(pin)) {
			throw new GpioException("Pin " + pin + " is not exported!");
		}
		return gpioPinMap.get(pin);
	}
	
	public Pin toRaspiPin(Integer pin) throws GpioException {
		Pin raspiPin = null;
			if (raspiPinMap.containsKey(pin)) {
				raspiPin = raspiPinMap.get(pin);
			} else {
				throw new GpioException("Pin "+pin+" could not be resolved to RaspiPin");
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
		unexportAll();
		gpio(null);
	}

	public PinState getState(GpioPin gpioPin) {
		return gpio.getState((GpioPinDigital) gpioPin);
	}

	public Collection<GpioPin> getExportedPins() {
		return gpioPinMap.values();
	}
	

	public Response newGpioPwmPin(final Integer pin) throws GpioException {
		return newGpioPwmPin(pin, GpioPwmConstants.OFF, GpioPwmConstants.DEFAULT_FREQUENCY);
	}

	public Response newGpioPwmPin(final Integer pin, final Integer power, final Integer frequency) throws GpioException {
		Response response;
		Pin raspiPin = toRaspiPin(pin);
		GpioPwmOutputPin gpioPwmPin;
		if (GpioPwmOutputPinController.getInstance().isNew(raspiPin)) {
			gpioPwmPin = new GpioPwmOutputPin(gpio(), raspiPin, power, frequency);
			response = GpioPwmOutputPinController.getInstance().startNewPwm(gpioPwmPin);
		}
		else {
			gpioPwmPin = GpioPwmOutputPinController.getInstance().restartPwm(raspiPin);
			response = Response.ok("Restarting PWM signal on pin "+gpioPwmPin.getPin().getAddress()+" ("+gpioPwmPin.getName()+")").build();
		}
		return response;
	}
	
}
