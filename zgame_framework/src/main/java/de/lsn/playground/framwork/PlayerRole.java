package de.lsn.playground.framwork;

public enum PlayerRole {
	
	GUEST("guest"),
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
