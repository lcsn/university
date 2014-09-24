package de.lsn.raspberrypi.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import de.lsn.raspberrypi.framework.MotorConstants;
import de.lsn.raspberrypi.framework.MotorAction;
import de.lsn.raspberrypi.logic.GpioHelper;
import de.lsn.raspberrypi.logic.MotorController;

@Path("/gpio/motor")
public class MotorService implements Serializable {

	private static final long serialVersionUID = 8163473394508632847L;

//	@Inject
//	private GpioHelper gpioHelper;
	
	@Inject
	private MotorController motorController;
	
//	#######################################
//	############ CONFIGURATION ############
//	#######################################
	
//	@GET
//	@Path("{engine}/forward/get")
//	public Response getForwardPin(@PathParam("engine") Integer engine) {
//		Status status = Status.BAD_REQUEST;
//		String msg = "";
//		try {
////			Pin pin = motorController.getForwardPinByEngineId(engine);
//			Integer pin = motorController.getForwardPinByEngineId(engine);
////			msg = "Engine \""+engine+"\" : Forward (Pin) >> "+pin.getName();
//			msg = "Engine \""+engine+"\" : Forward (Pin) >> "+gpioHelper.toRaspiPin(pin).getName();
//		} catch (Exception e) {
//			msg = e.getMessage();
//			e.printStackTrace();
//		}
//		System.out.println(msg);
//		return Response.status(status).entity(msg).build();
//	}
	
//	@GET
//	@Path("{engine}/backward/get")
//	public Response getBackwardPin(@PathParam("engine") Integer engine) {
//		Status status = Status.BAD_REQUEST;
//		String msg = "";
//		try {
////			Pin pin = motorController.getBackwardPinByEngineId(engine);
//			Integer pin = motorController.getBackwardPinByEngineId(engine);
////			msg = "Engine \""+engine+"\" : Backward (Pin) >> "+pin.getName();
//			msg = "Engine \""+engine+"\" : Backward (Pin) >> "+gpioHelper.toRaspiPin(pin).getName();
//		} catch (Exception e) {
//			msg = e.getMessage();
//			e.printStackTrace();
//		}
//		System.out.println(msg);
//		return Response.status(status).entity(msg).build();
//	}
	
	@PUT
	@Path("{engine}/enable/set/{pin}")
	public Response setEnablePin(@PathParam("engine") Integer engine, @PathParam("pin") Integer pin) {
		Status status = Status.BAD_REQUEST;
		String msg = "Engine \""+engine+"\" : Enable (Pin) >> "+pin;
		try {
			motorController.enable(MotorAction.ENABLE, engine, pin);
			status = Status.OK;
		} catch (Exception e) {
			msg = "Failed to set enable pin \""+pin+"\" of engine \""+engine+"\" > ("+e.getMessage()+")";
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("{engine}/turnleft/set/{pin}")
	public Response setTurnLeftPin(@PathParam("engine") Integer engine, @PathParam("pin") Integer pin) {
		Status status = Status.BAD_REQUEST;
		String msg = "Engine \""+engine+"\" : Turn (left) (Pin) >> "+pin;
		try {
			motorController.enable(MotorAction.LEFT_TURN, engine, pin);
			status = Status.OK;
		} catch (Exception e) {
			msg = "Failed to set turn pin \""+pin+"\" of engine \""+engine+"\" > ("+e.getMessage()+")";
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("{engine}/turnright/set/{pin}")
	public Response setTurnRightPin(@PathParam("engine") Integer engine, @PathParam("pin") Integer pin) {
		Status status = Status.BAD_REQUEST;
		String msg = "Engine \""+engine+"\" : Turn (right) (Pin) >> "+pin;
		try {
			motorController.enable(MotorAction.RIGHT_TURN, engine, pin);
			status = Status.OK;
		} catch (Exception e) {
			msg = "Failed to set turn pin \""+pin+"\" of engine \""+engine+"\" > ("+e.getMessage()+")";
			e.printStackTrace();
		}
		System.out.println(msg);
		return Response.status(status).entity(msg).build();
	}
//	
//	@PUT
//	@Path("{engine}/frequency/{frequency}")
//	public Response frequency(@PathParam("engine") Integer engine, @PathParam("frequency") Integer frequency) {
//		Status status = Status.BAD_REQUEST;
//		String msg = "Engine "+engine+" : Frequency >> "+frequency;
//		try {
//			motorController.adjustFrequency(engine, frequency);
//			status = Status.OK;
//		} catch (Exception e) {
//			msg = "Failed to set frequency of engine \""+engine+"\" to \""+frequency+"\" > ("+e.getMessage()+")";
//			e.printStackTrace();
//		}
//		System.out.println(msg);
//		return Response.status(status).entity(msg).build();
//	}
	
//	##################################
//	############ MOVEMENT ############
//	##################################
	
	@PUT
	@Path("{engine}/start")
	public Response start(@PathParam("engine") Integer engine) {
		ResponseBuilder responseBuilder = Response.status(Status.BAD_REQUEST);
		try {
			responseBuilder = Response.fromResponse(motorController.start(engine));
			responseBuilder.entity("Engine ["+engine+"] : PWM started");
		} catch (Exception e) {
			responseBuilder.entity("Engine ["+engine+"] : Failed to start! > ("+e.getMessage()+")");
			e.printStackTrace();
		}
		return responseBuilder.build();
	}
	
	@PUT
	@Path("{engine}/stop")
	public Response stop(@PathParam("engine") Integer engine) {
		ResponseBuilder responseBuilder = Response.status(Status.BAD_REQUEST);
		try {
			responseBuilder = Response.fromResponse(motorController.stop(engine));
			responseBuilder.entity("Engine ["+engine+"] : PWM stopped");
		} catch (Exception e) {
			responseBuilder.entity("Engine ["+engine+"] : Failed to stop! > ("+e.getMessage()+")");
			e.printStackTrace();
		}
		return responseBuilder.build();
	}
	
	@PUT
	@Path("{engine}/power/{power}")
	public Response power(@PathParam("engine") Integer engine, @PathParam("power") Integer power) {
		ResponseBuilder responseBuilder = Response.status(Status.BAD_REQUEST);
		try {
			responseBuilder = Response.fromResponse(motorController.power(engine, power));
			responseBuilder.entity("Engine ["+engine+"] > Power ["+power+"]");
		} catch (Exception e) {
			responseBuilder.entity("Engine ["+engine+"] : Failed to set power ["+power+"] > ("+e.getMessage()+")");
			e.printStackTrace();
		}
		return responseBuilder.build();
	}
	
	@PUT
	@Path("{engine}/frequency/{freq}")
	public Response cycle(@PathParam("engine") Integer engine, @PathParam("freq") Integer freq) {
		ResponseBuilder responseBuilder = Response.status(Status.BAD_REQUEST);
		try {
			responseBuilder = Response.fromResponse(motorController.cycle(engine, freq));
			responseBuilder.entity("Engine ["+engine+"] > Frequency ["+freq+"]");
		} catch (Exception e) {
			responseBuilder.entity("Engine ["+engine+"] : Failed to set frequency ["+freq+"] > ("+e.getMessage()+")");
			e.printStackTrace();
		}
		return responseBuilder.build();
	}
	
	@PUT
	@Path("{engine}/halt")
	public Response halt(@PathParam("engine") Integer engine) {
		ResponseBuilder responseBuilder = Response.status(Status.BAD_REQUEST);
		try {
			responseBuilder = Response.fromResponse(motorController.halt(engine));
			responseBuilder.entity("Engine ["+engine+"] : Halted");
		} catch (Exception e) {
			responseBuilder.entity("Engine ["+engine+"] : Failed to halt! > ("+e.getMessage()+")");
			e.printStackTrace();
		}
		return responseBuilder.build();
	}
	
	@PUT
	@Path("{engine}/turnleft")
	public Response turnleft(@PathParam("engine") Integer engine) {
		ResponseBuilder responseBuilder = Response.status(Status.BAD_REQUEST);
		try {
			responseBuilder = Response.fromResponse(motorController.turnLeft(engine));
			responseBuilder.entity("Engine ["+engine+"] : Turn left!");
		} catch (Exception e) {
			responseBuilder.entity("Engine ["+engine+"] : Failed turning left! > ("+e.getMessage()+")");
			e.printStackTrace();
		}
		return responseBuilder.build();
	}

	@PUT
	@Path("{engine}/turnright")
	public Response turnright(@PathParam("engine") Integer engine) {
		ResponseBuilder responseBuilder = Response.status(Status.BAD_REQUEST);
		try {
			responseBuilder = Response.fromResponse(motorController.turnRight(engine));
			responseBuilder.entity("Engine ["+engine+"] : Turn right!");
		} catch (Exception e) {
			responseBuilder.entity("Engine ["+engine+"] : Failed turning right! > ("+e.getMessage()+")");
			e.printStackTrace();
		}
		return responseBuilder.build();
	}
	
}
