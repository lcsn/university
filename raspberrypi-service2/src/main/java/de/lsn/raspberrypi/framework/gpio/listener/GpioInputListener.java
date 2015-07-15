package de.lsn.raspberrypi.framework.gpio.listener;

import de.lsn.raspberrypi.framework.gpio.event.GpioInputEvent;

public interface GpioInputListener {

	public void onInputChanged(GpioInputEvent event);
	
}
