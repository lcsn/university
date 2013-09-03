package de.lsn.playground.entity.attribute;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Defense extends Attribute {

	private Integer defenseValue;
	
	public Defense() {
		// TODO Auto-generated constructor stub
	}
	
	public Defense(Integer defenseValue) {
		this.defenseValue = defenseValue;
	}
	
	public Integer getDefenseValue() {
		return defenseValue;
	}
	
	public void setDefenseValue(Integer defenseValue) {
		this.defenseValue = defenseValue;
	}
	
}
