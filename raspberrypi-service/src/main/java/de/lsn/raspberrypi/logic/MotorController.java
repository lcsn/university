package de.lsn.raspberrypi.logic;

import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.lsn.raspberrypi.framework.MotorConstants;
import de.lsn.raspberrypi.framework.Rotation;
import de.lsn.raspberrypi.service.GpioPwmService;

@ApplicationScoped
public class MotorController implements Serializable {

	private static final long serialVersionUID = 4106384989876864573L;

	private static final String ENGINE_PREFIX = "engine";
	
//	private HashMap<String, String> engineIdMap = new HashMap<String, String>();
	
//	private HashMap<String, Pin> forwardPinMap;
	private HashMap<String, Integer> forwardPinMap;
//	private HashMap<String, Pin> backwardPinMap;
	private HashMap<String, Integer> backwardPinMap;

	@Inject
	private GpioPwmService gpioPwmService;

	@Inject
	private GpioHelper gpioHelper;

	private boolean started = false;
	
	@PostConstruct
	private void init() {
//		this.forwardPinMap = new HashMap<String, Pin>();
		this.forwardPinMap = new HashMap<String, Integer>();
//		this.backwardPinMap = new HashMap<String, Pin>();
		this.backwardPinMap = new HashMap<String, Integer>();
	}

//	public HashMap<String, Pin> getForwardPinMap() {
//		return forwardPinMap;
//	}
	
//	public HashMap<String, Pin> getBackwardPinMap() {
//		return backwardPinMap;
//	}

//	public Pin getForwardPinByEngineId(Integer engine) throws Exception {
//		String engineId = toEngineId(engine);
//		if (!forwardPinMap.containsKey(engineId)) {
//			throw new Exception("No forward pin set for engine \""+engine+"\"");
//		}
//		return forwardPinMap.get(engineId);
//	}
	
//	public Pin getBackwardPinByEngineId(Integer engine) throws Exception {
//		String engineId = toEngineId(engine);
//		if (!backwardPinMap.containsKey(engineId)) {
//			throw new Exception("No backward pin set for engine \""+engine+"\"");
//		}
//		return backwardPinMap.get(toEngineId(engine));
//	}
	
	public HashMap<String, Integer> getForwardPinMap() {
		return forwardPinMap;
	}
	
	public HashMap<String, Integer> getBackwardPinMap() {
		return backwardPinMap;
	}

	public Integer getForwardPinByEngineId(Integer engine) throws Exception {
		String engineId = toEngineId(engine);
		if (!forwardPinMap.containsKey(engineId)) {
			throw new Exception("No forward pin set for engine \""+engine+"\"");
		}
		return forwardPinMap.get(engineId);
	}
	
	public Integer getBackwardPinByEngineId(Integer engine) throws Exception {
		String engineId = toEngineId(engine);
		if (!backwardPinMap.containsKey(engineId)) {
			throw new Exception("No backward pin set for engine \""+engine+"\"");
		}
		return backwardPinMap.get(toEngineId(engine));
	}
	
	public String toEngineId(Integer engine) {
		return ENGINE_PREFIX+engine;
	}

	public void enable(Rotation rotation, Integer engine, Integer pin) throws Exception {
		switch (rotation) {
		case FORWARD:
			enableForward(engine, pin);
			break;
		case BACKWARD:
			enableBackward(engine, pin);
			break;
		}
	}
	
	private void enableForward(Integer engine, Integer pin) throws Exception {
		String engineId = toEngineId(engine);
		if (!forwardPinMap.containsKey(engineId)) {
//			forwardPinMap.put(engineId, gpioHelper.toRaspiPin(pin));
			forwardPinMap.put(engineId, pin);
			
//			if (backwardPinMap.containsKey(engineId)) {
//				backwardPinMap.remove(engineId);
//			}
		}
		else {
			throw new Exception("Pin "+pin+" already exists");
		}
	}
	
	private void enableBackward(Integer engine, Integer pin) throws Exception {
		String engineId = toEngineId(engine);
		if (!backwardPinMap.containsKey(engineId)) {
//			backwardPinMap.put(engineId, gpioHelper.toRaspiPin(pin));
			backwardPinMap.put(engineId, pin);
			
//			if (forwardPinMap.containsKey(engineId)) {
//				forwardPinMap.remove(engineId);
//			}
		}
		else {
			throw new Exception("Pin "+pin+" already exists");
		}
	}
	
	public Response start(Integer engine) throws Exception {
		if (!started ) {
			gpioHelper.startUp();
			started = true;
		}
//		Pin pin = getForwardPinByEngineId(engine);
		Integer forwardPin = getForwardPinByEngineId(engine);
		Integer backwardPin = getBackwardPinByEngineId(engine);
//		return gpioPwmService.startPwm(pin.getAddress(), MotorConstants.DEFAULT_STARTUP_POWER, MotorConstants.DEFAULT_FREQUENCY);
		Response response = gpioPwmService.startPwm(forwardPin, MotorConstants.DEFAULT_STARTUP_POWER, MotorConstants.DEFAULT_FREQUENCY);
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			return Response.status(Status.BAD_REQUEST).entity("Failed to forward pwm for engine: "+engine+" on pin: "+forwardPin).build();
		}
		response = gpioPwmService.startPwm(backwardPin, MotorConstants.DEFAULT_STARTUP_POWER, MotorConstants.DEFAULT_FREQUENCY);
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			return Response.status(Status.BAD_REQUEST).entity("Failed to forward pwm for engine: "+engine+" on pin: "+forwardPin).build();
		}
		return Response.ok().entity("Engine: "+engine+", Forward (Pin): "+forwardPin+", Backward (Pin): "+backwardPin).build();
	}
	
	public Response stop(Integer engine) throws Exception {
//		Pin pin = getForwardPinByEngineId(engine);
		Integer forwardPin = getForwardPinByEngineId(engine);
		Integer backwardPin = getBackwardPinByEngineId(engine);
//		return gpioPwmService.stopPwm(pin.getAddress());
//		return gpioPwmService.stopPwm(pin);
		Response response = gpioPwmService.stopPwm(forwardPin);
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			return Response.status(Status.BAD_REQUEST).entity("Failed to forward pwm for engine: "+engine+" on pin: "+forwardPin).build();
		}
		response = gpioPwmService.stopPwm(backwardPin);
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			return Response.status(Status.BAD_REQUEST).entity("Failed to forward pwm for engine: "+engine+" on pin: "+forwardPin).build();
		}
		return Response.ok().entity("Engine: "+engine+", Forward (Pin): "+forwardPin+", Backward (Pin): "+backwardPin).build();
	}

	public Response forward(Integer engine, Integer power) throws Exception {
//		return gpioPwmService.duty(getForwardPinByEngineId(engine).getAddress(), power);
		return gpioPwmService.duty(getForwardPinByEngineId(engine), power);
	}
	
	public Response backward(Integer engine, Integer power) throws Exception {
//		return gpioPwmService.duty(getBackwardPinByEngineId(engine).getAddress(), power);
		return gpioPwmService.duty(getBackwardPinByEngineId(engine), power);
	}

	public Response adjustFrequency(Integer pin, Integer freq) throws Exception {
		return gpioPwmService.frequency(pin, freq);
	}
	
}
