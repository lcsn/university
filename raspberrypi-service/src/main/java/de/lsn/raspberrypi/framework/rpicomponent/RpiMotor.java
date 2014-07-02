package de.lsn.raspberrypi.framework.rpicomponent;

import de.lsn.raspberrypi.framework.gpio.output.pwm.GpioPwmDigitalOutputPin;


public class RpiMotor extends RpiPwmOutputComponent {

	public RpiMotor(GpioPwmDigitalOutputPin digitalOutputPin) {
		super(digitalOutputPin);
	}
	
}
