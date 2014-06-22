package de.lsn.raspberrypi.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/greeting")
public class GreetingService {

	@GET
	public String message() {
		return "Hi REST!";
	}

	@GET
	@Path("/{name}")
	public String message(@PathParam("name") String name) {
		return "Hi, " + name + "!";
	}
	
	@POST
	public String lowerCase(final String message) {
		return message.toLowerCase();
	}

}
