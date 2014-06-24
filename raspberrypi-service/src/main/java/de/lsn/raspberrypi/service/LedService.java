package de.lsn.raspberrypi.service;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.lsn.raspberrypi.logic.LedPwmBean;

@Path("/led")
public class LedService {

	@Inject
	private LedPwmBean ledPwmBean;

	@POST
	@Path("/create/pwm/{pin}")
	public Response create(@PathParam("pin") Integer pin) {
		return ledPwmBean.createPwm(pin, 0, 100);
	}
	
	@POST
	@Path("/create/pwm/{pin}/{range}")
	public Response create(@PathParam("pin") Integer pin, @PathParam("range") Integer range) {
		return ledPwmBean.createPwm(pin, 0, range);
	}
	
	@POST
	@Path("/create/pwm/{pin}/{value}/{range}")
	public Response create(@PathParam("pin") Integer pin, @PathParam("value") Integer value, @PathParam("range") Integer range) {
		return ledPwmBean.createPwm(pin, value, range);
	}

	@PUT
	@Path("/fade/on/pwm/{pin}")
	public Response fadeOn(@PathParam("pin") Integer pin) {
		return ledPwmBean.fadeOn(pin, 0);
	}
	
	@PUT
	@Path("/fade/on/pwm/{pin}/{value}")
	public Response fadeOn(@PathParam("pin") Integer pin, @PathParam("value") Integer value) {
		return ledPwmBean.fadeOn(pin, value);
	}
	
	@PUT
	@Path("/fade/on/pwm/{pin}/{value}/{range}")
	public Response fadeOn(@PathParam("pin") Integer pin, @PathParam("value") Integer value, @PathParam("range") Integer range) {
		return ledPwmBean.fadeOn(pin, value, range);
	}
	
	@PUT
	@Path("/fade/off/pwm/{pin}")
	public Response fadeOff(@PathParam("pin") Integer pin) {
		return ledPwmBean.fadeOff(pin, 0);
	}
	
	@PUT
	@Path("/fade/off/pwm/{pin}/{value}")
	public Response fadeOff(@PathParam("pin") Integer pin, @PathParam("value") Integer value) {
		return ledPwmBean.fadeOff(pin, value);
	}
	
	@PUT
	@Path("/fadeoff/pwm/{pin}/{value}/{range}")
	public Response fadeOff(@PathParam("pin") Integer pin, @PathParam("value") Integer value, @PathParam("range") Integer range) {
		return ledPwmBean.fadeOff(pin, value, range);
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
