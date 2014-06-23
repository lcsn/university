package de.lsn.raspberrypi.logic;

import java.util.HashSet;
import java.util.concurrent.Callable;

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

	private HashSet<Integer> pwmPinSet = new HashSet<>();

	@PostConstruct
	private void init() {
		Gpio.wiringPiSetup();
	}

	public Response on(final Integer pin, final Integer range) {
		if (!pwmPinSet.contains(pin)) {
			SoftPwm.softPwmCreate(pin, 0, range);
			pwmPinSet.add(pin);
		}
		// // continuous loop
		// while (true) {
		// fade LED to fully ON
		for (int i = 0; i <= range; i++) {
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

		// fade LED to fully OFF
		// for (int i = 100; i >= 0; i--) {
		// value1.set(i);
		// Thread.sleep(25);
		// }
		// }

		return Response.ok().build();
	}

	public Response off(final Integer pin, final Integer range) {
		if (!pwmPinSet.contains(pin)) {
			SoftPwm.softPwmCreate(pin, 0, range);
			pwmPinSet.add(pin);
		}
		// // continuous loop
		// while (true) {
		// fade LED to fully ON
		for (int i = range; i >= 0; i--) {
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

		// fade LED to fully OFF
		// for (int i = 100; i >= 0; i--) {
		// value1.set(i);
		// Thread.sleep(25);
		// }
		// }

		return Response.ok().build();
	}

}
