package de.lsn.raspberrypi.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {

	protected Long id;
	
	private String firstname;
	
	private String lastname;
	
	public Person() {
		// TODO Auto-generated constructor stub
	}
	
	public Person(Long id, String firstname, String lastname) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
}
