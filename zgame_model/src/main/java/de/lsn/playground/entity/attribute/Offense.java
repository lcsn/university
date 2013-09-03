package de.lsn.playground.entity.attribute;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Offense extends Attribute  {

	private Integer offenseValue;
	
	public Offense() {
		// TODO Auto-generated constructor stub
	}
	
	public Offense(Integer offenseValue) {
		this.offenseValue = offenseValue;
	}
	
	public Integer getOffenseValue() {
		return offenseValue;
	}
	
	public void setOffenseValue(Integer offenseValue) {
		this.offenseValue = offenseValue;
	}
	
}
