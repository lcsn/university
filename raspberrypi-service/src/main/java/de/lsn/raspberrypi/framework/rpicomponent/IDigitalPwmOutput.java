package de.lsn.raspberrypi.framework.rpicomponent;

public interface IDigitalPwmOutput {

	public void fadeOn();
	
	public void fadeOff();
	
	public void fadeUpTo(Integer power);
	
	public void fadeDownTo(Integer power);
	
	public void powerTo(Integer power);

	public void setFadingSpeedByStep(Integer stepSize);

}
