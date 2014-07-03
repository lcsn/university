package de.lsn.raspberrypi.framework.gpio.output.pwm;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.impl.GpioPinImpl;

public class GpioPwmOutputPin extends GpioPinImpl implements Runnable {

	private int duty = 100;
	private int frequency = 1;
	private long dutycycle = 1000;
	private long pausecylce = 0;
	
	private GpioPinDigitalOutput gpioPin;
	
	private boolean active = false;
	private Thread mainThread;
	
	public GpioPwmOutputPin(GpioController gpio, Pin pin) {
		this(gpio, null, pin);
	}
	
	public GpioPwmOutputPin(GpioController gpio, GpioProvider provider, Pin pin) {
		super(gpio, provider, pin);
		this.gpioPin = gpio.provisionDigitalOutputPin(pin);
		this.mainThread = Thread.currentThread();
	}
	
	@Override
	public void run() {
		active = true;
		while(active) {
//			System.out.println("Duty: "+dutycycle+", Pause: "+pausecylce);
			gpioPin.pulse(dutycycle, true);
			gpioPin.low();
			if (pausecylce > 0) {
				asleep(pausecylce);
			}
		}
		System.out.println("PWM on "+gpioPin.getName()+" is stopping");
		synchronized (mainThread) {
			mainThread.notify();
		}
	}
	
	public void stop() {
		if (active) {
			active = false;
			synchronized (mainThread) {
				try {
					mainThread.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			gpioPin.low();
		}
		System.out.println("PWM on "+gpioPin.getName()+" stopped");
	}
	
	public void adjustDuty(int duty) {
		System.out.println("Adjusting duty");
		System.out.println(this.duty +" -> "+duty);
		this.duty = duty;
		this.dutycycle = GpioPwmValueProvider.getInstance().getValue(duty, this.frequency)[0];
		this.pausecylce = GpioPwmValueProvider.getInstance().getValue(duty, this.frequency)[1];
		System.out.println("New dutycycle: "+this.dutycycle);
		System.out.println("New pausecycle: "+this.pausecylce);
	}

	public void adjustFrequency(Integer frequency) {
		System.out.println("Adjusting frequency");
		System.out.println(this.frequency +" -> "+frequency);
		this.frequency = frequency;
		this.dutycycle = GpioPwmValueProvider.getInstance().getValue(this.duty, frequency)[0];
		this.pausecylce = GpioPwmValueProvider.getInstance().getValue(this.duty, frequency)[1];
		System.out.println("New dutycycle: "+this.dutycycle);
		System.out.println("New pausecycle: "+this.pausecylce);
	}
	
	private void asleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public GpioPinDigitalOutput getGpioPin() {
		return gpioPin;
	}

	public int getDuty() {
		return duty;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
}
