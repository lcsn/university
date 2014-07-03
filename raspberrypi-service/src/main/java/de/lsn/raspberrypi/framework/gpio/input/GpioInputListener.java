package de.lsn.raspberrypi.framework.gpio.input;

public interface GpioInputListener {

	public void onInputChanged(GpioInputEvent event);
	
}
