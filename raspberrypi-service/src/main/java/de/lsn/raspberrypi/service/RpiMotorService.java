package de.lsn.raspberrypi.service;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("led")
public class RpiMotorService {

	@PUT
	@Path("/new/{pin}")
	public void newLed() {

	}
	
}
