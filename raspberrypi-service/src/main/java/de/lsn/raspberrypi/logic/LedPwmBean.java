package de.lsn.raspberrypi.logic;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

import de.lsn.raspberrypi.framework.GpioEvent;

@Singleton
public class LedPwmBean {

	@Inject
	private Event<GpioEvent> gpioEvent;

	private HashMap<Integer, Integer> pwmPinMap = new HashMap<Integer, Integer>();

	@PostConstruct
	private void init() {
		Gpio.wiringPiSetup();
	}

	public Response createPwm(final Integer pin, final Integer value, Integer range) {
		if (!pwmPinMap.containsKey(pin) || pwmPinMap.get(pin) != range) {
			SoftPwm.softPwmCreate(pin, value, range);
			pwmPinMap.put(pin, range);
		}
		return Response.ok().build();
	}
	
	public Response fadeOn(final Integer pin, final Integer value) {
		return fadeOn(pin, value, 100);
	}
	
	public Response fadeOn(final Integer pin, final Integer value, final Integer range) {
		createPwm(pin, value, range);
		for (int i = value; i <= range; i++) {
			try {
				final int j = i;
				gpioEvent.fire(new GpioEvent(new Runnable() {

					@Override
					public void run() {
						SoftPwm.softPwmWrite(pin, j);
					}
				}));
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return Response.ok().build();
	}
	
	public Response fadeOff(final Integer pin, final Integer value) {
		return fadeOff(pin, value, 100);
	}
	
	public Response fadeOff(final Integer pin, final Integer value, final Integer range) {
		createPwm(pin, value, range);
		for (int i = range; i >= value; i--) {
			try {
				final int j = i;
				gpioEvent.fire(new GpioEvent(new Runnable() {

					@Override
					public void run() {
						SoftPwm.softPwmWrite(pin, j);
					}
				}));
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return Response.ok().build();
	}
	
}
