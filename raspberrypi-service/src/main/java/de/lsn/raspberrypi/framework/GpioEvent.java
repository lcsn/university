package de.lsn.raspberrypi.framework;


public class GpioEvent {

	private Runnable runnable;

	public GpioEvent(Runnable runnable) {
		this.runnable = runnable;
	}

	public Runnable getRunnable() {
		return runnable;
	}
	
}
