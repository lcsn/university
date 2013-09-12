package de.lsn.playground.framwork;

public enum PlayerRole {

	DEFAULT("default"),
	ADMIN("admin");

	private String value;

	private PlayerRole(String value) {
		this.value = value;
	}
	
	public String val() {
		return value;
	}
	
}
