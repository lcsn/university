package de.lsn.raspberrypi.service;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@Path("/led")
public class LedService {

	@GET
	@Path("/test")
	public Response test() {
		System.out.println("<--Pi4J--> GPIO Control Example ... started.");
		// create gpio controller
		final GpioController gpio = GpioFactory.getInstance();
		// provision gpio pin #01 as an output pin and turn on
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_17, "MyLED", PinState.HIGH);
		System.out.println("--> GPIO state should be: ON");
		System.out.println("--> PinState of " + pin.getName() + " is " + pin.getState());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// turn off gpio pin #01
		pin.low();
		System.out.println("--> GPIO state should be: OFF");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// toggle the current state of gpio pin #01 (should turn on)
		pin.toggle();
		System.out.println("--> GPIO state should be: ON");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// toggle the current state of gpio pin #01 (should turn off)
		pin.toggle();
		System.out.println("--> GPIO state should be: OFF");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// turn on gpio pin #01 for 1 second and then off
		System.out.println("--> GPIO state should be: ON for only 1 second");
		pin.pulse(1000, true); // set second argument to 'true' use a blocking
								// call
		// stop all GPIO activity/threads by shutting down the GPIO controller
		// (this method will forcefully shutdown all GPIO monitoring threads and
		// scheduled tasks)
		gpio.shutdown();
		return Response.status(Status.OK).build();
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
