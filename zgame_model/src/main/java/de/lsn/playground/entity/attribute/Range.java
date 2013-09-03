package de.lsn.playground.framwork.attribute;

import javax.persistence.Transient;


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

	public abstract Integer getRangeValue();
	
}
