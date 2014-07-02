package de.lsn.raspberrypi.framework.rpicomponent;

import de.lsn.raspberrypi.framework.gpio.output.pwm.GpioPwmDigitalOutputPin;


public class RpiLed extends RpiPwmOutputComponent {

	public RpiLed(GpioPwmDigitalOutputPin digitalOutputPin) {
		super(digitalOutputPin);
	}
	
}
