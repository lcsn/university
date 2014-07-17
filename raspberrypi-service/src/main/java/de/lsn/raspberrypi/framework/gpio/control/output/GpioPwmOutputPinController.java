package de.lsn.raspberrypi.framework.gpio.control.output;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.pi4j.io.gpio.Pin;

import de.lsn.raspberrypi.framework.gpio.output.pwm.GpioPwmOutputPin;

public class GpioPwmOutputPinController {

	private ConcurrentHashMap<Pin, GpioPwmOutputPin> gpioPwmPinMap = new ConcurrentHashMap<Pin, GpioPwmOutputPin>();
	private static GpioPwmOutputPinController instance;
	public ExecutorService newCachedThreadPool;

	public GpioPwmOutputPinController() {
		newCachedThreadPool = Executors.newCachedThreadPool();
	}
	
	public static GpioPwmOutputPinController getInstance() {
		if (null == instance) {
			instance = new GpioPwmOutputPinController();
		}
		return instance;
	}
	
	public Response startNewPwm(GpioPwmOutputPin gpioPwmPin) {
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
	
	public GpioPwmOutputPin restartPwm(Pin pin) {
//		Response response;
		GpioPwmOutputPin gpioPwmPin = null;
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
	
	public Response stopPwm(Pin pin) throws Exception {
		Response response = Response.ok("Stopping pwm signal on pin "+pin.getAddress()).build();
		if (gpioPwmPinMap.containsKey(pin)) {
			gpioPwmPinMap.get(pin).stop();
		}
		else {
			throw new Exception("No pwm on pin "+pin.getAddress()+" started");
		}
		return response;
	}
	
	public void destroy() {
		for (GpioPwmOutputPin gpioPwmPin: gpioPwmPinMap.values()) {
			gpioPwmPin.stop();
		}
		newCachedThreadPool.shutdown();
	}

	public Response adjustDuty(Pin pin, Integer duty) throws Exception {
		Response response = Response.ok("Adjusted duty of pin "+pin.getAddress()+" to "+duty).build();
		if (gpioPwmPinMap.containsKey(pin)) {
			gpioPwmPinMap.get(pin).adjustDuty(duty);
		}
		else {
			throw new Exception("No pwm on pin "+pin.getAddress()+" started");
		}
		return response;
	}

	public Response adjustFrequency(Pin pin, Integer frequency) throws Exception {
		Response response = Response.ok("Adjusted frequency of pin "+pin.getAddress()+" to "+frequency).build();
		if (gpioPwmPinMap.containsKey(pin)) {
			gpioPwmPinMap.get(pin).adjustFrequency(frequency);
		}
		else {
			throw new Exception("No pwm on pin "+pin.getAddress()+" started");
		}
		return response;
	}

	public boolean isNew(Pin pin) {
		return !gpioPwmPinMap.containsKey(pin);
	}
	
}
