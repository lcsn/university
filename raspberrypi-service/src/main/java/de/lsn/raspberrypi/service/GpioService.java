package de.lsn.raspberrypi.service;

import java.util.Collection;

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
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.PinMode;

import de.lsn.raspberrypi.framework.GpioException;
import de.lsn.raspberrypi.logic.GpioHelper;

@Path("/gpio")
public class GpioService {

	@Inject
	private GpioHelper gpioHelper;
	
	@GET
	@Path("/pins/list")
	public Response list() {
		Status status = Status.CONFLICT;
		StringBuilder result = new StringBuilder("Pins:\n");
		try {
			 Collection<GpioPin> provisionedPins = gpioHelper.gpio().getProvisionedPins();
			if (null != provisionedPins && provisionedPins.size() > 0) {
				for (GpioPin gpioPin : gpioHelper.getExportedPins()) {
					result.append(gpioPin.getName() + " Exported? " + (gpioPin.isExported() ? "Ja" : "Nein") + " State? " + (gpioHelper.getState(gpioPin).toString()));
					result.append("\n");
				}
			}
			else {
				result.append("No pins provisioned");
			}
			status = Status.OK;
		} catch (GpioException e) {
			e.printStackTrace();
			result.append(e.getMessage());
		}
		return Response.status(status).entity(result.toString()).build();
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
	
	@PUT
	@Path("/pins/{pin}/export/{mode}")
	public Response export(@PathParam("pin") Integer pin, @PathParam("mode") String mode) {
		Status status = Status.CONFLICT;
		String msg = "";
		try {
			PinMode pinMode = gpioHelper.toPinMode(mode);
			GpioPin pinOut = gpioHelper.toGpioPin(pinMode.getDirection(), pin);
			gpioHelper.gpio().export(pinMode, pinOut);
			if (pinOut.isExported()) {
				gpioHelper.export(pin, pinOut);
				msg = "Pin \""+pin+"\" with mode \""+mode+"\" exported";
				status = Status.OK;
			}
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
				gpioHelper.gpio().unexport(gpioPin);
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
			msg = "Pin "+pin+" has state: " + gpioHelper.gpio().getState(gpioPin);
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
			msg = "Pin "+pin+" has state: " + gpioHelper.gpio().getState(gpioPin);
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
			msg = "Pin "+pin+" has state: " + gpioHelper.gpio().getState(gpioPin);
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

	@POST
	@Path("/startup")
	public Response startup() {
		gpioHelper.startUp();
		return Response.status(Status.OK).entity("Startup successful").build();
	}
	
	@POST
	@Path("/shutdown")
	public Response shutdown() {
		Status status = Status.CONFLICT;
		String msg = "";
		try {
			gpioHelper.unexportAll();
			gpioHelper.gpio().unexportAll();
			gpioHelper.gpio().shutdown();
			gpioHelper.shutdown();
			msg = "Shutdown successful";
			status = Status.OK;
		} catch (GpioException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return Response.status(status).entity(msg).build();
	}
	
	@PUT
	@Path("/pwm/test/{step}")
	public Response testPwm(@PathParam("step") Integer step) {
		try {
			GpioPinPwmOutput pwmPin = gpioHelper.getPwmPin();
			for (int i = 0; i < 1024; i+=step) {
				System.out.println("Add " + i);
				pwmPin.setPwm(i);
			}
			for (int i = 1024; i < 0; i-=step) {
				System.out.println("Sub " + i);
				pwmPin.setPwm(i);
			}
		} catch (GpioException e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}
	
}
