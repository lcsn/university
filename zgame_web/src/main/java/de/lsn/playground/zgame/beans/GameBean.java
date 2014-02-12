package de.lsn.playground.zgame.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class GameBean implements Serializable {

	private boolean pause = false;
	private String show;
	
	@PostConstruct
	private void init() {
		System.out.println("GameBean constructed");
	}
	
	public void setShow(String s) {
		this.show = s;
	}
	
	public void pauseGame() {
		System.out.println("Pause game");
		this.pause=true;
	}
	
	public void continueGame() {
		System.out.println("Continue game");
		this.pause=false;
	}
	
	public boolean isPause() {
		return pause;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public String getShow() {
		return show;
	}
	
}
