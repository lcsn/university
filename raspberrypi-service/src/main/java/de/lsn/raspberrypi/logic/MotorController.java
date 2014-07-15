package de.lsn.raspberrypi.logic;

import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.pi4j.io.gpio.Pin;

@ApplicationScoped
public class MotorController implements Serializable {

	private static final long serialVersionUID = 4106384989876864573L;

//	private HashMap<String, String> engineIdMap = new HashMap<String, String>();
	
	private HashMap<String, Pin> forwardPinMap;
	private HashMap<String, Pin> backwardPinMap;

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
	
}
