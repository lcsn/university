package de.lsn.playground.entity;

import javax.persistence.Embeddable;

import de.lsn.playground.entity.attribute.Attribute;

@SuppressWarnings("serial")
@Embeddable
public class Name extends Attribute {

	private String nameValue;
	
	public Name() {
		// TODO Auto-generated constructor stub
	}
	
	public Name(String name) {
		this.nameValue = name;
	}

	public String getNameValue() {
		return nameValue;
	}

	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}
	
}
