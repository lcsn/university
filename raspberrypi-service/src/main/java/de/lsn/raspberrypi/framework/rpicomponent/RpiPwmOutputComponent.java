package de.lsn.raspberrypi.framework.rpicomponent;

import de.lsn.raspberrypi.framework.RpiConstants;
import de.lsn.raspberrypi.framework.pwm.GpioPwmPinDigitalOutput;

public class RpiPwmOutputComponent implements IDigitalPwmOutput {

	private int fadingStepSize = RpiConstants.DEFAULT_FADING_SPEED;
	
	private GpioPwmPinDigitalOutput gpioPwmPin;
	
	public RpiPwmOutputComponent(GpioPwmPinDigitalOutput gpioPwmPin) {
		this.gpioPwmPin = gpioPwmPin;
	}

	@Override
	public void fadeOn() {
		fadeUpTo(RpiConstants.MAX_DUTY);
	}

	@Override
	public void fadeOff() {
		fadeDownTo(RpiConstants.MIN_DUTY);
	}

	@Override
	public void fadeUpTo(Integer power) {
		if (power > gpioPwmPin.getDuty()) {
			for (int i = gpioPwmPin.getDuty(); i < power; i+=fadingStepSize) {
				gpioPwmPin.adjustDuty(i);	
			}		
		}
	}

	@Override
	public void fadeDownTo(Integer power) {
		if (power < gpioPwmPin.getDuty()) {
			for (int i = gpioPwmPin.getDuty(); i > power; i-=fadingStepSize) {
				gpioPwmPin.adjustDuty(i);	
			}
		}
	}

	@Override
	public void powerTo(Integer power) {
		gpioPwmPin.adjustDuty(power);
	}

	@Override
	public void setFadingSpeedByStep(Integer step) {
		this.fadingStepSize = step;
	}

}
