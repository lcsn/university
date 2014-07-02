package de.lsn.raspberrypi.framework.pwm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.pi4j.io.gpio.Pin;

public class GpioPwmPinController {

	private ConcurrentHashMap<Pin, GpioPwmPinDigitalOutput> gpioPwmPinMap = new ConcurrentHashMap<Pin, GpioPwmPinDigitalOutput>();
	private static GpioPwmPinController instance;
	public ExecutorService newCachedThreadPool;

	public GpioPwmPinController() {
		newCachedThreadPool = Executors.newCachedThreadPool();
	}
	
	public static GpioPwmPinController getInstance() {
		if (null == instance) {
			instance = new GpioPwmPinController();
		}
		return instance;
	}
	
	public Response startNewPwm(GpioPwmPinDigitalOutput gpioPwmPin) {
		Response response = Response.ok("Starting PWM signal on pin "+gpioPwmPin.getPin().getAddress()+" ("+gpioPwmPin.getName()+")").build();
		try {
			gpioPwmPinMap.put(gpioPwmPin.getPin(), gpioPwmPin);
			newCachedThreadPool.execute(gpioPwmPin);
		} catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		return response;
	}
	
	public GpioPwmPinDigitalOutput restartPwm(Pin pin) {
//		Response response;
		GpioPwmPinDigitalOutput gpioPwmPin = null;
		try {
			if (gpioPwmPinMap.containsKey(pin)) {
				gpioPwmPin = gpioPwmPinMap.get(pin);
				newCachedThreadPool.execute(gpioPwmPin);
//				response = Response.ok("Starting PWM signal on pin " + pin.getAddress() + " (" + gpioPwmPin.getName() + ")").build();
//			}
//			else {
//				response = Response.status(Status.NOT_FOUND).entity("Cannot restart - No PWM signal on pin " + pin.getAddress()).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
//			response = Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		return gpioPwmPin;
	}
	
	public Response stopPwm(Pin pin) {
		Response response = Response.ok("Stopping pwm signal on pin "+pin.getAddress()).build();
		if (gpioPwmPinMap.containsKey(pin)) {
			gpioPwmPinMap.get(pin).stop();
		}
		else {
			response = Response.status(Status.NOT_FOUND).entity("No pwm on pin "+pin.getAddress()+" started").build();
		}
		return response;
	}
	
	public void destroy() {
		for (GpioPwmPinDigitalOutput gpioPwmPin: gpioPwmPinMap.values()) {
			gpioPwmPin.stop();
		}
		newCachedThreadPool.shutdown();
	}

	public Response adjustDuty(Pin pin, Integer duty) {
		Response response = Response.ok("Adjusted duty of pin "+pin.getAddress()+" to "+duty).build();
		if (gpioPwmPinMap.containsKey(pin)) {
			gpioPwmPinMap.get(pin).adjustDuty(duty);
		}
		else {
			response = Response.status(Status.NOT_FOUND).entity("No pwm on pin "+pin.getAddress()+" started").build();
		}
		return response;
	}

	public Response adjustFrequency(Pin pin, Integer frequency) {
		Response response = Response.ok("Adjusted frequency of pin "+pin.getAddress()+" to "+frequency).build();
		if (gpioPwmPinMap.containsKey(pin)) {
			gpioPwmPinMap.get(pin).adjustFrequency(frequency);
		}
		else {
			response = Response.status(Status.NOT_FOUND).entity("No pwm on pin "+pin.getAddress()+" started").build();
		}
		return response;
	}

	public boolean isNew(Pin pin) {
		return !gpioPwmPinMap.containsKey(pin);
	}
	
}
