package de.lsn.raspberrypi.framework.gpio.dev.servo;

import java.util.concurrent.TimeUnit;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;

public class Servo implements Runnable {

	private Integer angle = new Integer(90);

	private GpioPinDigitalOutput gpioPin;

	private boolean active = false;
	private boolean isAdjusting = true;
	private Long[] cycle;
	
	private Thread mainThread;

	public Servo(GpioController gpio, Pin pin) {
		this(gpio, null, pin);
	}

	public Servo(GpioController gpio, GpioProvider provider, Pin pin) {
		this.gpioPin = gpio.provisionDigitalOutputPin(pin);
		this.mainThread = Thread.currentThread();
		init();
	}

	private void init() {
		this.gpioPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF, PinMode.DIGITAL_OUTPUT);
//		adjustAngle(angle, 50 + 2 * angle);
		this.cycle = ServoValueProvider.RIGHT; 
	}

	@Override
	public void run() {
		active = true;
		while (active) {
			try {
				if (isAdjusting) {
					gpioPin.high();
					TimeUnit.NANOSECONDS.sleep(cycle[0]);
					gpioPin.low();
					TimeUnit.NANOSECONDS.sleep(cycle[1]);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
	}

	public void adjustAngle(final int _angle) {
		adjustAngle(_angle, 100 * (Math.abs(_angle - getAngle())));
	}

	public void updateCycle() {
		cycle = ServoValueProvider.getInstance().angleToDuty(angle);
	}
	
	public void adjustAngle(final int _angle, final int timeout) {
		angle = _angle;
		updateCycle();
//		if (!isAdjusting) {
//			isAdjusting = true;
//			TimerTask task = new TimerTask() {
//
//				@Override
//				public void run() {
//					try {
//						System.out.println("Timeout: " + timeout);
//						TimeUnit.MILLISECONDS.sleep(timeout);
//						isAdjusting = false;
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			};
//			task.run();
//			run();
//		}
	}
	
	public GpioPinDigitalOutput getGpioPin() {
		return gpioPin;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getAngle() {
		return angle;
	}

	public void setCycle(Long[] cycle) {
		this.cycle = cycle;
	}
	
}
