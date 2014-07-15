package de.lsn.raspberrypi.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.pi4j.io.gpio.Pin;

import de.lsn.raspberrypi.logic.GpioHelper;
import de.lsn.raspberrypi.logic.MotorController;

@Path("/gpio/motor")
public class MotorService implements Serializable {

	private static final long serialVersionUID = 8163473394508632847L;

	@Inject
	private GpioHelper gpioHelper;
	
	@Inject
	private MotorController motorController;
	
	private static final String ENGINE_PREFIX = "engine";
	
//	#######################################
//	############ CONFIGURATION ############
//	#######################################
	
	@GET
	@Path("{engine}/forward/get")
	public Response getForwardPin(@PathParam("engine") Integer engine) {
		String engineId = ENGINE_PREFIX + engine;
		Status status = Status.BAD_REQUEST;
		String msg = "No forward pin set for engine " + engineId;
		if (motorController.getForwardPinMap().containsKey(engineId)) {
			msg = "Forward pin for engine "+engineId+" set to " +motorController.getForwardPinMap().get(engineId).getName();
			status = Status.OK;
		}
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("{engine}/forward/set/{pin}")
	public Response setForwardPin(@PathParam("engine") Integer engine, @PathParam("pin") Integer pin) {
		String engineId = ENGINE_PREFIX + engine;
		Status status = Status.BAD_REQUEST;
		Pin raspiPin = gpioHelper.toRaspiPin(pin);
		String msg = "Failed to set forward pin " + raspiPin.getName() + " for engine " + engineId;
		if (motorController.getForwardPinMap().containsKey(engineId)) {
			motorController.getForwardPinMap().remove(engineId);
		}
		motorController.getForwardPinMap().put(engineId, raspiPin);
		msg = "Engine " + engineId + " forward pin set to " + raspiPin.getName();
		status = Status.OK;
		return Response.status(status).entity(msg).build();
	}
	
	@GET
	@Path("{engine}/backward/get")
	public Response getBackwardPin(@PathParam("engine") Integer engine) {
		String engineId = ENGINE_PREFIX + engine;
		Status status = Status.BAD_REQUEST;
		String msg = "No backward pin set for engine " + engineId;
		if (motorController.getBackwardPinMap().containsKey(engineId)) {
			msg = "Backward pin for engine " + engineId + " set to " + motorController.getBackwardPinMap().get(engineId).getName();
			status = Status.OK;
		}
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("{engine}/backward/set/{pin}")
	public Response setBackwardPin(@PathParam("engine") Integer engine, @PathParam("pin") Integer pin) {
		String engineId = ENGINE_PREFIX + engine;
		Status status = Status.BAD_REQUEST;
		Pin raspiPin = gpioHelper.toRaspiPin(pin);
		String msg = "Failed to set backward pin " + raspiPin.getName() + " for engine " + engineId;
		if (motorController.getBackwardPinMap().containsKey(engineId)) {
			motorController.getBackwardPinMap().remove(engineId);
		}
		motorController.getBackwardPinMap().put(engineId, raspiPin);
		msg = "Engine " + engineId + " backward pin set to " + raspiPin.getName();
		status = Status.OK;
		return Response.status(status).entity(msg).build();
	}
	
//	##################################
//	############ MOVEMENT ############
//	##################################
	
	@PUT
	@Path("{engine}/forward/{power}")
	public Response forward(@PathParam("engine") Integer engine, @PathParam("power") Integer power) {
		String msg = "Forward > Engine: " + engine+ " , Power: " + power;
		System.out.println(msg);
		return Response.ok().entity(msg).build();
	}
	
	@PUT
	@Path("{engine}/backward/{power}")
	public Response backward(@PathParam("engine") Integer engine, @PathParam("power") Integer power) {
		String msg = "Backward > Engine: " + engine+ " , Power: " + power;
		System.out.println(msg);
		return Response.ok().entity(msg).build();
	}
	
	@PUT
	@Path("{engine}/stop")
	public Response stop(@PathParam("engine") Integer engine) {
		String msg = "Stop > Engine: " + engine;
		System.out.println(msg);
		return Response.ok().entity(msg).build();
	}
	
}
