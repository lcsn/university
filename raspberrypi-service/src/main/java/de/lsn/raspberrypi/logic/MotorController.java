package de.lsn.raspberrypi.logic;

import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.pi4j.io.gpio.PinMode;

import de.lsn.raspberrypi.framework.MotorAction;
import de.lsn.raspberrypi.framework.MotorConstants;
import de.lsn.raspberrypi.service.GpioPwmService;
import de.lsn.raspberrypi.service.GpioService;

@ApplicationScoped
public class MotorController implements Serializable {

	private static final long serialVersionUID = 4106384989876864573L;

	private static final String ENGINE_PREFIX = "engine";
	
//	private HashMap<String, String> engineIdMap = new HashMap<String, String>();
	
	private HashMap<String, Integer> enablePinMap;
//	private HashMap<String, Pin> forwardPinMap;
	private HashMap<String, Integer> leftTurnPinMap;
//	private HashMap<String, Pin> backwardPinMap;
	private HashMap<String, Integer> rightTurnPinMap;

	@Inject
	private GpioService gpioService;
	
	@Inject
	private GpioPwmService gpioPwmService;

	@Inject
	private GpioHelper gpioHelper;

	private boolean started = false;
	
	@PostConstruct
	private void init() {
		this.enablePinMap = new HashMap<String, Integer>();
//		this.forwardPinMap = new HashMap<String, Pin>();
		this.leftTurnPinMap = new HashMap<String, Integer>();
//		this.backwardPinMap = new HashMap<String, Pin>();
		this.rightTurnPinMap = new HashMap<String, Integer>();
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
	
//	public HashMap<String, Integer> getForwardPinMap() {
//		return leftTurnPinMap;
//	}
	
//	public HashMap<String, Integer> getBackwardPinMap() {
//		return rightTurnPinMap;
//	}
	
	public Integer getEnablePinByEngineId(Integer engine) throws Exception {
		String engineId = toEngineId(engine);
		if (!enablePinMap.containsKey(engineId)) {
			throw new Exception("No enable pin set for engine \""+engine+"\"");
		}
		return enablePinMap.get(engineId);
	}

	public Integer getLeftTurnPinByEngineId(Integer engine) throws Exception {
		String engineId = toEngineId(engine);
		if (!leftTurnPinMap.containsKey(engineId)) {
			throw new Exception("No left turn pin set for engine \""+engine+"\"");
		}
		return leftTurnPinMap.get(engineId);
	}
	
	public Integer getRightTurnPinByEngineId(Integer engine) throws Exception {
		String engineId = toEngineId(engine);
		if (!rightTurnPinMap.containsKey(engineId)) {
			throw new Exception("No right turn pin set for engine \""+engine+"\"");
		}
		return rightTurnPinMap.get(toEngineId(engine));
	}
	
	public String toEngineId(Integer engine) {
		return ENGINE_PREFIX+engine;
	}

	public void enable(MotorAction action, Integer engine, Integer pin) throws Exception {
		switch (action) {
		case ENABLE:
			enableMotor(engine, pin);
			break;
		case LEFT_TURN:
			enableLeftTurn(engine, pin);
			break;
		case RIGHT_TURN:
			enableRightTurn(engine, pin);
			break;
		}
	}
	
	private void enableMotor(Integer engine, Integer pin) throws Exception {
		String engineId = toEngineId(engine);
		if (!enablePinMap.containsKey(engineId)) {
//			forwardPinMap.put(engineId, gpioHelper.toRaspiPin(pin));
			enablePinMap.put(engineId, pin);
			
//			if (backwardPinMap.containsKey(engineId)) {
//				backwardPinMap.remove(engineId);
//			}
		}
		else {
			throw new Exception("Pin "+pin+" already exists");
		}
	}
	
	private void enableLeftTurn(Integer engine, Integer pin) throws Exception {
		String engineId = toEngineId(engine);
		if (!rightTurnPinMap.containsKey(engineId)) {
//			backwardPinMap.put(engineId, gpioHelper.toRaspiPin(pin));
			rightTurnPinMap.put(engineId, pin);
			
//			if (forwardPinMap.containsKey(engineId)) {
//				forwardPinMap.remove(engineId);
//			}
		}
		else {
			throw new Exception("Pin "+pin+" already exists");
		}
	}
	
	private void enableRightTurn(Integer engine, Integer pin) throws Exception {
		String engineId = toEngineId(engine);
		if (!leftTurnPinMap.containsKey(engineId)) {
//			forwardPinMap.put(engineId, gpioHelper.toRaspiPin(pin));
			leftTurnPinMap.put(engineId, pin);
			
//			if (backwardPinMap.containsKey(engineId)) {
//				backwardPinMap.remove(engineId);
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
		Integer enablePin = getEnablePinByEngineId(engine);
		Integer leftTurnPin = getLeftTurnPinByEngineId(engine);
		Integer rightTurnPin = getRightTurnPinByEngineId(engine);
		Response response = gpioPwmService.startPwm(enablePin, MotorConstants.DEFAULT_STARTUP_POWER, MotorConstants.DEFAULT_FREQUENCY);
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			return Response.status(Status.BAD_REQUEST).entity("Failed to start pwm for engine: "+engine+" on pin: "+enablePin).build();
		}
		
		gpioService.export(leftTurnPin, PinMode.DIGITAL_OUTPUT.getName());
		gpioService.export(rightTurnPin, PinMode.DIGITAL_OUTPUT.getName());
		
		return Response.ok().entity("Engine: "+engine+" on Pin: "+enablePin+" started. Duty: "+MotorConstants.DEFAULT_STARTUP_POWER+", Freq: "+MotorConstants.DEFAULT_FREQUENCY).build();
		
////		Pin pin = getForwardPinByEngineId(engine);
//		Integer forwardPin = getLeftTurnPinByEngineId(engine);
//		Integer backwardPin = getRightTurnPinByEngineId(engine);
////		return gpioPwmService.startPwm(pin.getAddress(), MotorConstants.DEFAULT_STARTUP_POWER, MotorConstants.DEFAULT_FREQUENCY);
//		Response response = gpioPwmService.startPwm(forwardPin, MotorConstants.DEFAULT_STARTUP_POWER, MotorConstants.DEFAULT_FREQUENCY);
//		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
//			return Response.status(Status.BAD_REQUEST).entity("Failed to forward pwm for engine: "+engine+" on pin: "+forwardPin).build();
//		}
//		response = gpioPwmService.startPwm(backwardPin, MotorConstants.DEFAULT_STARTUP_POWER, MotorConstants.DEFAULT_FREQUENCY);
//		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
//			return Response.status(Status.BAD_REQUEST).entity("Failed to forward pwm for engine: "+engine+" on pin: "+forwardPin).build();
//		}
//		return Response.ok().entity("Engine: "+engine+", Forward (Pin): "+forwardPin+", Backward (Pin): "+backwardPin).build();
	}
	
	public Response stop(Integer engine) throws Exception {
		Integer enablePin = getEnablePinByEngineId(engine);
		Response response = gpioPwmService.stopPwm(enablePin);
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			return Response.status(Status.BAD_REQUEST).entity("Failed to stop pwm for engine: "+engine+" on pin: "+enablePin).build();
		}
		return Response.ok().entity("Engine: "+engine+" on Pin: "+enablePin+" stopped!").build();
		
////		Pin pin = getForwardPinByEngineId(engine);
//		Integer forwardPin = getLeftTurnPinByEngineId(engine);
//		Integer backwardPin = getRightTurnPinByEngineId(engine);
////		return gpioPwmService.stopPwm(pin.getAddress());
////		return gpioPwmService.stopPwm(pin);
//		Response response = gpioPwmService.stopPwm(forwardPin);
//		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
//			return Response.status(Status.BAD_REQUEST).entity("Failed to forward pwm for engine: "+engine+" on pin: "+forwardPin).build();
//		}
//		response = gpioPwmService.stopPwm(backwardPin);
//		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
//			return Response.status(Status.BAD_REQUEST).entity("Failed to forward pwm for engine: "+engine+" on pin: "+forwardPin).build();
//		}
//		return Response.ok().entity("Engine: "+engine+", Forward (Pin): "+forwardPin+", Backward (Pin): "+backwardPin).build();
	}

	public Response power(Integer engine, Integer power) throws Exception {
//		return gpioPwmService.duty(getForwardPinByEngineId(engine).getAddress(), power);
		return gpioPwmService.duty(getEnablePinByEngineId(engine), power);
	}
	
	public Response cycle(Integer engine, Integer freq) throws Exception {
//		return gpioPwmService.duty(getForwardPinByEngineId(engine).getAddress(), power);
		return gpioPwmService.frequency(getEnablePinByEngineId(engine), freq);
	}
	
	public Response turnRight(Integer engine) throws Exception {
		gpioService.low(getLeftTurnPinByEngineId(engine));
		gpioService.high(getRightTurnPinByEngineId(engine));
		return Response.ok("Engine ["+engine+"] turns right!").build();
//		return gpioPwmService.duty(getForwardPinByEngineId(engine).getAddress(), power);
//		return gpioPwmService.duty(getLeftTurnPinByEngineId(engine), power);
	}
	
	public Response turnLeft(Integer engine) throws Exception {
		gpioService.low(getRightTurnPinByEngineId(engine));
		gpioService.high(getLeftTurnPinByEngineId(engine));
		return Response.ok("Engine ["+engine+"] turns left!").build();
//		return gpioPwmService.duty(getBackwardPinByEngineId(engine).getAddress(), power);
//		return gpioPwmService.duty(getRightTurnPinByEngineId(engine), power);
	}

	public Response halt(Integer engine) throws Exception {
		gpioService.low(getLeftTurnPinByEngineId(engine));
		gpioService.low(getRightTurnPinByEngineId(engine));
		return Response.ok("Engine ["+engine+"] halted!").build();
	}
	
//	public Response adjustFrequency(Integer pin, Integer freq) throws Exception {
//		return gpioPwmService.frequency(pin, freq);
//	}
	
}
