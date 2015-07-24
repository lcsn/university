package de.lsn.raspberrypi.service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.pi4j.io.gpio.Pin;

import de.lsn.raspberrypi.framework.gpio.dev.servo.ServoController;
import de.lsn.raspberrypi.framework.gpio.dev.servo.ServoValueProvider;
import de.lsn.raspberrypi.framework.gpio.exception.GpioException;
import de.lsn.raspberrypi.logic.GpioHelper;

@Named
@Path("/gpio/dev/servo")
public class GpioServoService {

	@Inject
	private GpioHelper gpioHelper;
	
	@PUT
	@Path("/start/{pin}")
	public Response startServo(@PathParam("pin") Integer pin) {
		Response response;
		try {
			response = ServoController.getInstance().startServo(gpioHelper.gpio(), gpioHelper.toRaspiPin(pin));
		} catch (GpioException e) {
			e.printStackTrace();
			response = Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		return response;
	}
	
	@PUT
	@Path("/stop/{pin}")
	public Response stopServo(@PathParam("pin") Integer pin) {
		Response response;
		try {
			response = ServoController.getInstance().stopServo(gpioHelper.toRaspiPin(pin));
		} catch (GpioException e) {
			e.printStackTrace();
			response = Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		return response;
	}
	
	@PUT
	@Path("/adjust/{pin}/angle/{angle}")
	public Response angle(@PathParam("pin") Integer pin, @PathParam("angle") Integer angle) throws Exception {
		return ServoController.getInstance().adjustAngle(gpioHelper.toRaspiPin(pin), angle);
	}
	
	@PUT
	@Path("/adjust/frequency/{frequency}")
	public Response frequency(@PathParam("frequency") Integer frequency) throws Exception {
		return ServoController.getInstance().adjustFrequency(frequency);
	}
	
	@POST
	@Path("/test/{pin}")
	public Response TEST(@PathParam("pin") final Integer pin) throws Exception {
		final Pin raspiPin = gpioHelper.toRaspiPin(pin);
		final Object[] animation = {
//			ServoValueProvider.LEFT,
//			ServoValueProvider.CENTER,
			ServoValueProvider.RIGHT,
//			,
			ServoValueProvider.LEFT
		};
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < animation.length; j++) {
						try {
							Long[] cycle = (Long[]) animation[j];
							ServoController.getInstance().setCycle(raspiPin, cycle);
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		t.start();
		t.join();
		
		return Response.ok("Test gestartet.. ").build();
	}
	
}
