package de.lsn.playground.zgame.websocket;

import de.lsn.playground.MotionCoder;


public class MotionBean {

	public static class MotionBeanCode extends MotionCoder {
	}
	
	private String playerName;
	private String x;
	private String y;

	public MotionBean() {
	}

	public MotionBean(String playerName, String x, String y) {
		super();
		this.playerName = playerName;
		this.x = x;
		this.y = y;
	}

	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	
}
