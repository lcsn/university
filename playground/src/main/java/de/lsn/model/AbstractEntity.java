package de.lsn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractEntity {

	private ID id;
	
	public AbstractEntity() {
		id = new ID();
	}
	
	@JsonIgnore
	public String getId() {
		return String.valueOf(id.getValue());
	}
	
}
