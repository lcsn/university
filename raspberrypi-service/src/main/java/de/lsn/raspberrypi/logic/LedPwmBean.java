package de.lsn.raspberrypi.logic;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

	private boolean blink = false;
	
	@Inject
	private Event<GpioEvent> gpioEvent;

	private HashMap<Integer, Integer> pwmPinMap = new HashMap<Integer, Integer>();

	private ExecutorService newCachedThreadPool;

	@PostConstruct
	private void init() {
		Gpio.wiringPiSetup();
		newCachedThreadPool = Executors.newCachedThreadPool();
	}

	public Response createPwm(final Integer pin, final Integer value, Integer range) {
		int result = -1;
		if (!pwmPinMap.containsKey(pin)) {
//		if (!pwmPinMap.containsKey(pin) || pwmPinMap.get(pin) != range) {
			result = SoftPwm.softPwmCreate(pin, value, range);
			pwmPinMap.put(pin, range);
		}
		return Response.ok("Creating pwm result: "+result).build();
	}
	
	public Response test(final Integer pin, final Integer msec) {
		createPwm(pin, 0, 100);
		System.out.println("Pin "+pin+"--> 30");
		SoftPwm.softPwmWrite(pin, 30);
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Pin "+pin+"--> 60");
		SoftPwm.softPwmWrite(pin, 30);
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Pin "+pin+"--> 90");
		SoftPwm.softPwmWrite(pin, 30);
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Pin "+pin+"--> 60");
		SoftPwm.softPwmWrite(pin, 30);
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Pin "+pin+"--> 30");
		SoftPwm.softPwmWrite(pin, 30);
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Pin "+pin+"--> 0");
		SoftPwm.softPwmWrite(pin, 0);
		return Response.ok("Test completed").build();
	}
	
	public Response off(final Integer pin) {
		String msg = "";
		if (blink) {
			this.blink = false;
			msg = "Pin "+pin+" should be off now";
		}
		else {
			msg = "Pin "+pin+" is off - Turn it on to turn it off again";
		}
		return Response.ok(msg).build();
	}
	
	public Response on(final Integer pin, final Integer value, final Integer range, final  Integer freq) {
		createPwm(pin, value, range);
		newCachedThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				blink = true;
				while (blink) {
					try {
						SoftPwm.softPwmWrite(pin, value);
						Thread.sleep(1000/freq);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}				
			}
		});
		return Response.ok("Pulse on pin: "+pin+", value: "+value+", freq: "+freq+"hz").build();
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
