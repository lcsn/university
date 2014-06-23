package de.lsn.raspberrypi.service;

import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.lsn.raspberrypi.logic.LedPwmBean;

@Path("/led")
public class LedService {

	@Inject
	private LedPwmBean ledPwmBean;
	
	@PUT
	@Path("/on/pwm")
	public Response onPwm() {
//		Neuer Future typ geht nicht da keine java 8,
//		aber alter Future typ würde gehen.
		return ledPwmBean.on();
	}
	
	@PUT
	@Path("/off/pwm")
	public Response offPwm() {
		return ledPwmBean.off();
	}
	
	@PUT
	@Path("/on")
	public Response on(Boolean b) {
		Status status = Status.BAD_REQUEST;
		if (b) {
			status = Status.OK;
		}
		return Response.status(status).build();
	}

	@PUT
	@Path("/off")
	public Response off(Boolean b) {
		Status status = Status.BAD_REQUEST;
		if (b) {
			status = Status.OK;
		}
		return Response.status(status).build();
	}

	@PUT
	@Path("/ok")
	public Response ok() {
		return Response.ok("Everything is fine!", MediaType.TEXT_PLAIN).build();
	}

	@PUT
	@Path("/fail")
	public Response fail() {
		return Response.status(Status.BAD_REQUEST).entity("Everything is wrong!").build();
	}

}
