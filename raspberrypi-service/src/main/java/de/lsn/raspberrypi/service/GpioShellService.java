package de.lsn.raspberrypi.service;

import java.io.IOException;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Named
@Path("/gpio/shell")
public class GpioShellService {

	@GET
	@Path("/greet")
	public String greet() {
		try {
			Runtime.getRuntime().exec("sudo echo Greetings from RaspberryPi!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Greetings from RaspberryPi!";
	}
	
	@POST
	@Path("/pins/{pin}/export")
	public void export(@PathParam("pin") Integer pin) {
		try {
			Runtime.getRuntime().exec("sudo echo "+pin+" > /sys/class/gpio/export");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/pins/{pin}/unexport")
	public void unexport(@PathParam("pin") Integer pin) {
		try {
			Runtime.getRuntime().exec("sudo echo "+pin+" > /sys/class/gpio/unexport");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@PUT
	@Path("/pins/{pin}/write/{value}")
	public void write(@PathParam("pin") Integer pin, @PathParam("value") Integer value) {
		try {
			Runtime.getRuntime().exec("sudo echo "+value+" > /sys/class/gpio/gpio"+pin+"/value");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}