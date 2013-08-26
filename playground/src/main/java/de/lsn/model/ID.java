package de.lsn.model;

import java.util.UUID;

public class ID {

	protected Long value;

	public ID() {
		this.value = UUID.randomUUID().getLeastSignificantBits();
	}

	public Long getValue() {
		return value;
	}
	
}
