package de.lsn.raspberrypi;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.jboss.logging.Logger;

@Named
@SessionScoped
public class MotorController implements Serializable {

	private static final long serialVersionUID = 914148107909122422L;

	private static final Logger log = Logger.getLogger(MotorController.class);
	
	private String url;
	
	public void forward() {
		log.info("forward pressed");
	}
	
	public void stop() {
		log.info("backward pressed");
	}
	
	public void backward() {
		log.info("backward pressed");
	}
	
	public void turnLeft() {
		log.info("turn left pressed");
	}
	
	public void turnRight() {
		log.info("turn right pressed");
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
