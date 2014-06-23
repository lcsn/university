package de.lsn.raspberrypi.framework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.event.Observes;

public class GpioExecutor {

	private ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
	
	public void handleGpioEvent(@Observes GpioEvent gpioEvent) {
		newCachedThreadPool.execute(gpioEvent.getRunnable());
	}
	
}
