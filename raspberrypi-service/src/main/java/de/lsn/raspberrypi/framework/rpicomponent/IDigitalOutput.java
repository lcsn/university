package de.lsn.raspberrypi.framework.rpicomponent;

public interface IDigitalOutput {

	public void high();
	
	public void low();
	
	public void pulse(long msec);
	
	public void toggle();
	
}
