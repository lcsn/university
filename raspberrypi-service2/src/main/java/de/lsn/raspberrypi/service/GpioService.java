package de.lsn.raspberrypi.service;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinMode;

import de.lsn.raspberrypi.framework.gpio.exception.GpioException;
import de.lsn.raspberrypi.logic.GpioHelper;

@Path("/gpio")
public class GpioService {

	@Inject
	private GpioHelper gpioHelper;
	
//	#############################
//	############ GET ############
//	#############################
	
	@GET
	@Path("/pins/{pin}/state")
	public Response state(@PathParam("pin") Integer pin) {
		Status status = Status.CONFLICT;
		String msg = "";
		GpioPin gpioPin = null;
		try {
			gpioPin = gpioHelper.toGpioPin(pin);
			if (null != gpioPin) {
				msg = "State of Pin " + pin + " - " + gpioHelper.getState(gpioPin);
				status = Status.OK;
			}
			else {
				msg = "Pin "+pin+" is not exported";
			}
		} catch (GpioException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return Response.status(status).entity(msg).build();
	}
	
	@GET
	@Path("/pins/{pin}/name")
	public Response name(@PathParam("pin") Integer pin) {
		Status status = Status.CONFLICT;
		String msg = "";
		GpioPin gpioPin = null;
		try {
			gpioPin = gpioHelper.toGpioPin(pin);
			msg = "Name of Pin "+pin+" - "+gpioPin.getName();
			status = Status.OK;
		} catch (GpioException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return Response.status(status).entity(msg).build();
	}
	
	@GET
	@Path("/pins/{pin}/mode")
	public Response mode(@PathParam("pin") Integer pin) {
		Status status = Status.CONFLICT;
		String msg = "";
		try {
			GpioPin gpioPin = gpioHelper.toGpioPin(pin);
			msg = "Mode of Pin "+pin+" - "+gpioPin.getMode().getName();
		} catch (GpioException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return Response.status(status).entity(msg).build();
	}
	
//	#############################
//	############ PUT ############
//	#############################
	
	@PUT
	@Path("/pins/{pin}/export/{mode}")
	public Response export(@PathParam("pin") Integer pin, @PathParam("mode") String mode) {
		Status status = Status.CONFLICT;
		String msg = "";
		try {
			PinMode pinMode = gpioHelper.toPinMode(mode);
			GpioPin gpioPin = gpioHelper.newGpioPin(pin, pinMode.getDirection());
			if (!gpioPin.isExported()) {
				gpioHelper.gpio().export(pinMode, gpioPin);
			}
			msg = "Pin \""+pin+"\" with mode \""+mode+"\" exported";
			status = Status.OK;
		} catch (GpioException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("/pins/{pin}/unexport")
	public Response unexport(@PathParam("pin") Integer pin) {
		Status status = Status.CONFLICT;
		String msg = "";
		try {
			GpioPin gpioPin = gpioHelper.toGpioPin(pin);
			if (gpioPin.isExported()) {
				gpioHelper.unexport(pin);
				msg = "Pin \""+pin+"\" unexported";
				status = Status.OK;
			}
			else {
				throw new GpioException("Pin "+pin+" is not exported");
			}
		} catch (GpioException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("/pins/{pin}/high")
	public Response high(@PathParam("pin") Integer pin) {
		Status status = Status.CONFLICT;
		String msg = "";
		try {
			final GpioPinDigitalOutput gpioPin = (GpioPinDigitalOutput) gpioHelper.toGpioPin(pin);
			gpioPin.high();
			msg = "Pin "+pin+" has state: " + gpioHelper.getState(gpioPin);
			status = Status.OK;
		} catch (GpioException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("/pins/{pin}/low")
	public Response low(@PathParam("pin") Integer pin) {
		Status status = Status.CONFLICT;
		String msg = "";
		try {
			final GpioPinDigitalOutput gpioPin = (GpioPinDigitalOutput) gpioHelper.toGpioPin(pin);
			gpioPin.low();
			msg = "Pin "+pin+" has state: " + gpioHelper.getState(gpioPin);
			status = Status.OK;
		} catch (GpioException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("/pins/{pin}/toggle")
	public Response toggle(@PathParam("pin") Integer pin) {
		Status status = Status.CONFLICT;
		String msg = "";
		try {
			final GpioPinDigitalOutput gpioPin = (GpioPinDigitalOutput) gpioHelper.toGpioPin(pin);
			gpioPin.toggle();
			msg = "Pin "+pin+" has state: " + gpioHelper.getState(gpioPin);
			status = Status.OK;
		} catch (GpioException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("/pins/{pin}/pulse/{msec}")
	public Response pulse(@PathParam("pin") Integer pin, @PathParam("msec") Long msec) {
		Status status = Status.CONFLICT;
		String msg = "";
		try {
			final GpioPinDigitalOutput gpioPin = (GpioPinDigitalOutput) gpioHelper.toGpioPin(pin);
			gpioPin.pulse(msec);
			msg = "Pin "+pin+" pulse for: "+msec+"msec";
			status = Status.OK;
		} catch (GpioException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return Response.status(status).entity(msg).build();
	}

//	##############################
//	############ POST ############
//	##############################
	
	@POST
	@Path("/startup")
	public Response startup() {
		gpioHelper.startUp();
		return Response.status(Status.OK).entity("Startup successful!").build();
	}
	
	@POST
	@Path("/shutdown")
	public Response shutdown() {
		Status status = Status.CONFLICT;
		String msg = "";
		try {
			gpioHelper.shutdown();
			gpioHelper.gpio().shutdown();
			if (gpioHelper.gpio().isShutdown()) {
				msg = "Shutdown successful!";
				status = Status.OK;
			}
			gpioHelper.gpio(null);
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return Response.status(status).entity(msg).build();
	}
	
}
