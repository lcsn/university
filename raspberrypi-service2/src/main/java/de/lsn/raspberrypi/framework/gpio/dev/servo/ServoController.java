package de.lsn.raspberrypi.framework.gpio.dev.servo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.Pin;

public class ServoController {

	private static ServoController instance;
	private ConcurrentHashMap<Pin, Servo> servoMap = new ConcurrentHashMap<Pin, Servo>();
	
	public ExecutorService newCachedThreadPool;
	
	public ServoController() {
		newCachedThreadPool = Executors.newCachedThreadPool();
	}
	
	public static ServoController getInstance() {
		if (null == instance) {
			instance = new ServoController();
		}
		return instance;
	}
	
	public Response startServo(GpioController gpio, Pin pin) {
		String msg = "Servo an Pin \""+pin+"\" wird initialisiert ..";
		Status status = Status.OK;
		Servo servo;
		if (isNew(pin)) {
			servo = new Servo(gpio, pin);
			servoMap.put(pin, servo);
			msg+="Abgeschlossen.";
		} else {
			servo = servoMap.get(pin);
			msg+="Servo exisitiert bereits - Starte neu ..";
		}
		newCachedThreadPool.execute(servo);
		return Response.status(status).entity(msg).build();
	}
	
	public Response stopServo(Pin pin) {
		String msg = "Servo an Pin \""+pin+"\" wird gestoppt ..";
		Status status = Status.OK;
		if (!isNew(pin)) {
			Servo servo = servoMap.get(pin);
			servo.stop();
			msg+="Abgeschlossen.";
		} else {
			msg+="Kein Servo an Pin \""+pin+"\" initialisiert.";
		}
		return Response.status(status).entity(msg).build();
	}
	
	public Response adjustAngle(Pin pin, int angle) {
		String msg = "Winkel ("+angle+") wird eingestellt ..";
		Status status = Status.OK;
		if (!isNew(pin)) {
			Servo servo = servoMap.get(pin);
			servo.adjustAngle(angle);
			msg+="Abgeschlossen.";
		}
		else {
			msg+="Kein Servo an Pin \""+pin+"\" initialisiert.";
			status = Status.CONFLICT;
		}
		return Response.status(status).entity(msg).build();
	}
	
	public Response adjustFrequency(int frequency) {
		String msg = "Frequenz ("+frequency+") wird eingestellt ..";
		Status status = Status.OK;
		ServoValueProvider.getInstance().setFrequency(frequency);
		for (Servo servo : servoMap.values()) {
			servo.updateCycle();
		}
		return Response.status(status).entity(msg).build();
	}

	public void setCycle(Pin pin, Long[] cycle) {
		Servo servo = getServo(pin);
		if (null != servo) {
			servo.setCycle(cycle);
		}
	}

	public Servo getServo(Pin pin) {
		Servo servo = null;
		if (!isNew(pin)) {
			servo = servoMap.get(pin);
		}
		return servo;
	}
	
	private boolean isNew(Pin pin) {
		return !servoMap.containsKey(pin);
	}
	
	public void unexport(Pin pin, GpioController gpio) throws Exception {
		stopServo(pin);
		Servo servo = servoMap.remove(pin);
//		servo.getGpioPin().unexport();

		gpio.unexport(servo.getGpioPin());
		gpio.unprovisionPin(servo.getGpioPin());
	}
	
	public void unexportAll(GpioController gpio) throws Exception {
		if (null != servoMap && servoMap.size() > 0) {
			for (Servo servo : servoMap.values()) {
				unexport(servo.getGpioPin().getPin(), gpio);
			}
		}
		newCachedThreadPool.shutdown();
	}
	
}
