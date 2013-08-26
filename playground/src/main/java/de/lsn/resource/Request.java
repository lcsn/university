package de.lsn.resource;

public enum Request {

	GET,
	PUT;
	
	public String toString() {
		return this.name().toUpperCase();
	}
	
}
