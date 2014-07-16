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

import de.lsn.raspberrypi.framework.Rotation;
import de.lsn.raspberrypi.logic.MotorController;

@Path("/gpio/motor")
public class MotorService implements Serializable {

	private static final long serialVersionUID = 8163473394508632847L;

	@Inject
	private MotorController motorController;
	
//	#######################################
//	############ CONFIGURATION ############
//	#######################################
	
	@GET
	@Path("{engine}/forward/get")
	public Response getForwardPin(@PathParam("engine") Integer engine) {
		Status status = Status.BAD_REQUEST;
		String msg = "";
		try {
			Pin pin = motorController.getForwardPinByEngineId(engine);
			msg = "Engine \""+engine+"\" : Forward >> "+pin.getName();
		} catch (Exception e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.status(status).entity(msg).build();
	}
	
	@GET
	@Path("{engine}/backward/get")
	public Response getBackwardPin(@PathParam("engine") Integer engine) {
		Status status = Status.BAD_REQUEST;
		String msg = "";
		try {
			Pin pin = motorController.getBackwardPinByEngineId(engine);
			msg = "Engine \""+engine+"\" : Backward >> "+pin.getName();
		} catch (Exception e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("{engine}/forward/set/{pin}")
	public Response setForwardPin(@PathParam("engine") Integer engine, @PathParam("pin") Integer pin) {
		Status status = Status.BAD_REQUEST;
		String msg = "Engine \""+engine+"\" : Forward >> "+pin;
		try {
			motorController.enable(Rotation.FORWARD, engine, pin);
			status = Status.OK;
		} catch (Exception e) {
			msg = "Failed to set forward pin \""+pin+"\" of engine \""+engine+"\" > ("+e.getMessage()+")";
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.status(status).entity(msg).build();
		
	}
	
	@PUT
	@Path("{engine}/backward/set/{pin}")
	public Response setBackwardPin(@PathParam("engine") Integer engine, @PathParam("pin") Integer pin) {
		Status status = Status.BAD_REQUEST;
		String msg = "Engine \""+engine+"\" : Backward >> "+pin;
		try {
			motorController.enable(Rotation.BACKWARD, engine, pin);
			status = Status.OK;
		} catch (Exception e) {
			msg = "Failed to set backward pin \""+pin+"\" of engine \""+engine+"\" > ("+e.getMessage()+")";
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("{engine}/frequency/{frequency}")
	public Response frequency(@PathParam("engine") Integer engine, @PathParam("frequency") Integer frequency) {
		Status status = Status.BAD_REQUEST;
		String msg = "Engine "+engine+" : Frequency >> "+frequency;
		try {
			motorController.adjustFrequency(engine, frequency);
			status = Status.OK;
		} catch (Exception e) {
			msg = "Failed to set frequency of engine \""+engine+"\" to \""+frequency+"\" > ("+e.getMessage()+")";
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.status(status).entity(msg).build();
	}
	
//	##################################
//	############ MOVEMENT ############
//	##################################
	
	@PUT
	@Path("{engine}/start")
	public Response start(@PathParam("engine") Integer engine) {
		String msg = "";
		try {
//			TODO starten in eigenen Threads?! Wie ist das Verhalten beim Start?
			motorController.start(engine);
			msg = "Engines: STARTED";
		} catch (Exception e) {
			msg = "Failure > ("+e.getMessage()+")";
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.ok().entity(msg).build();
	}
	
	@PUT
	@Path("{engine}/stop")
	public Response stop(@PathParam("engine") Integer engine) {
		String msg = "";
		try {
			motorController.stop(engine);
			msg = "Engine "+engine+" : STOP";
		} catch (Exception e) {
			msg = "Failed to stop engine \""+engine+"\" > ("+e.getMessage()+")";
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.ok().entity(msg).build();
	}
	
	@PUT
	@Path("{engine}/forward/{power}")
	public Response forward(@PathParam("engine") Integer engine, @PathParam("power") Integer power) {
		String msg = "";
		try {
			motorController.forward(engine, power);
			msg = "Engine "+engine+" : Forward (Power) >> "+power;
		} catch (Exception e) {
			msg = "Failed to set backward power of engine \""+engine+"\" to \""+power+"\" > ("+e.getMessage()+")";
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.ok().entity(msg).build();
	}
	
	@PUT
	@Path("{engine}/backward/{power}")
	public Response backward(@PathParam("engine") Integer engine, @PathParam("power") Integer power) {
		String msg = "";
		try {
			motorController.backward(engine, power);
			msg = "Engine "+engine+" : Backward (Power) >> "+power;
		} catch (Exception e) {
			msg = "Failed to set forward power of engine \""+engine+"\" to \""+power+"\" > ("+e.getMessage()+")";
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.ok().entity(msg).build();
	}
	
}
