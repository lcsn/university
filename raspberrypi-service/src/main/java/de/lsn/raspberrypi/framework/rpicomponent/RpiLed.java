package de.lsn.raspberrypi.framework.rpicomponent;

import de.lsn.raspberrypi.framework.pwm.GpioPwmPinDigitalOutput;


public class RpiLed extends RpiPwmOutputComponent {

	public RpiLed(GpioPwmPinDigitalOutput digitalOutputPin) {
		super(digitalOutputPin);
	}
	
}
