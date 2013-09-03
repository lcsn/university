package de.lsn.playground.entity.attribute;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Health extends Attribute {

	private Integer healthPoints;
	
	public Health() {
		// TODO Auto-generated constructor stub
	}
	
	public Health(Integer healthPoints) {
		this.healthPoints = healthPoints;
	}
	
	public Integer getHealthPoints() {
		return healthPoints;
	}
	
	public void setHealthPoints(Integer healthPoints) {
		this.healthPoints = healthPoints;
	}
	
}
