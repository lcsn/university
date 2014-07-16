package de.lsn.raspberrypi.logic;

import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.pi4j.io.gpio.Pin;

import de.lsn.raspberrypi.framework.Rotation;

@ApplicationScoped
public class MotorController implements Serializable {

	private static final long serialVersionUID = 4106384989876864573L;

	private static final String ENGINE_PREFIX = "engine";
	
//	private HashMap<String, String> engineIdMap = new HashMap<String, String>();
	
	private HashMap<String, Pin> forwardPinMap;
	private HashMap<String, Pin> backwardPinMap;

//	@Inject
//	private GpioPwmService gpioPwmService;

//	@Inject
//	private GpioHelper gpioHelper;
	
	@PostConstruct
	private void init() {
		this.forwardPinMap = new HashMap<String, Pin>();
		this.backwardPinMap = new HashMap<String, Pin>();
	}

	public HashMap<String, Pin> getForwardPinMap() {
		return forwardPinMap;
	}
	
	public HashMap<String, Pin> getBackwardPinMap() {
		return backwardPinMap;
	}

	public Pin getForwardPinByEngineId(Integer engine) throws Exception {
		String engineId = toEngineId(engine);
		if (!forwardPinMap.containsKey(engineId)) {
			throw new Exception("No forward pin set for engine \""+engine+"\"");
		}
		return forwardPinMap.get(engineId);
	}
	
	public Pin getBackwardPinByEngineId(Integer engine) throws Exception {
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
//			if (forwardPinMap.containsKey(engineId)) {
//				forwardPinMap.remove(engineId);
//			}
		}
		else {
			throw new Exception("Pin "+pin+" already exists");
		}
	}
	
	public void start(Integer engine) throws Exception {
		Pin pin = getForwardPinByEngineId(engine);
		if (null == pin) {
			pin = getBackwardPinByEngineId(engine);
		}
//		gpioPwmService.startPwm(pin.getAddress(), MotorConstants.DEFAULT_STARTUP_POWER, MotorConstants.DEFAULT_FREQUENCY);
	}
	
	public void stop(Integer engine) throws Exception {
		Pin pin = getForwardPinByEngineId(engine);
		if (null == pin) {
			pin = getBackwardPinByEngineId(engine);
		}
//		gpioPwmService.stopPwm(pin.getAddress());
	}

	public void forward(Integer engine, Integer power) throws Exception {
//		gpioPwmService.duty(getForwardPinByEngineId(engine).getAddress(), power);
	}
	
	public void backward(Integer engine, Integer power) throws Exception {
//		gpioPwmService.duty(getBackwardPinByEngineId(engine).getAddress(), power);
	}

	public void adjustFrequency(Integer pin, Integer freq) {
//		gpioPwmService.frequency(pin, freq);
	}
	
}
