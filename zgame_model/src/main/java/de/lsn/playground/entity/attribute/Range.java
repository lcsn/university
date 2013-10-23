package de.lsn.playground.entity.attribute;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


@SuppressWarnings("serial")
public abstract class Range extends Attribute {

	@Transient
	private String rangeType;
	
	public Range() {
		// TODO Auto-generated constructor stub
	}
	
	public Range(String rangeType) {
		this.rangeType = rangeType;
	}
	
	public String getRangeType() {
		return this.rangeType;
	}

	@JsonIgnore
	public abstract Integer getRangeValue();
	
}
