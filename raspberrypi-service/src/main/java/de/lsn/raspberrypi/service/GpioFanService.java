package de.lsn.raspberrypi.service;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/gpio/fan")
public class GpioFanService {

	@Inject
	private GpioShellService gpioShellService;
	
	@POST
	@Path("/on/{pin}")
	public void on(@PathParam("pin") Integer pin) {
		gpioShellService.export(pin);
		gpioShellService.write(pin, new Integer(1));
	}
	
	@POST
	@Path("/off/{pin}")
	public void off(@PathParam("pin") Integer pin) {
		gpioShellService.write(pin, new Integer(0));
		gpioShellService.unexport(pin);
	}
	
}