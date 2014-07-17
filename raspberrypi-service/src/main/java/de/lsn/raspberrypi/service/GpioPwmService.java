package de.lsn.raspberrypi.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.lsn.raspberrypi.framework.gpio.control.output.GpioPwmOutputPinController;
import de.lsn.raspberrypi.framework.gpio.exception.GpioException;
import de.lsn.raspberrypi.logic.GpioHelper;

@Named
@Path("/gpio/pwm")
public class GpioPwmService {

	@Inject
	private GpioHelper gpioHelper;
	
	@PUT
	@Path("/start/{pin}")
	public Response startPwm(@PathParam("pin") Integer pin) {
		Response response;
		try {
			response = GpioPwmOutputPinController.getInstance().startNewPwm(gpioHelper.newGpioPwmPin(pin));
		} catch (GpioException e) {
			response = Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		return response;
	}
	
	@PUT
	@Path("/start/{pin}/{power}/{frequency}")
	public Response startPwm(@PathParam("pin") Integer pin, @PathParam("power") Integer power, @PathParam("frequency") Integer frequency) throws GpioException {
		return GpioPwmOutputPinController.getInstance().startNewPwm(gpioHelper.newGpioPwmPin(pin, power, frequency));
	}
	
	@PUT
	@Path("/stop/{pin}")
	public Response stopPwm(@PathParam("pin") Integer pin) throws Exception {
		return GpioPwmOutputPinController.getInstance().stopPwm(gpioHelper.toRaspiPin(pin));
	}

	@PUT
	@Path("/adjust/{pin}/duty/{duty}")
	public Response duty(@PathParam("pin") Integer pin, @PathParam("duty") Integer duty) throws Exception {
		return GpioPwmOutputPinController.getInstance().adjustDuty(gpioHelper.toRaspiPin(pin), duty);
	}
	
	@PUT
	@Path("/adjust/{pin}/frequency/{freq}")
	public Response frequency(@PathParam("pin") Integer pin, @PathParam("freq") Integer freq) throws Exception {
		return GpioPwmOutputPinController.getInstance().adjustFrequency(gpioHelper.toRaspiPin(pin), freq);
	}
	
}
