package de.lsn.raspberrypi.framework.rpicomponent;

import de.lsn.raspberrypi.framework.pwm.GpioPwmPinDigitalOutput;


public class RpiMotor extends RpiPwmOutputComponent {

	public RpiMotor(GpioPwmPinDigitalOutput digitalOutputPin) {
		super(digitalOutputPin);
	}
	
}
