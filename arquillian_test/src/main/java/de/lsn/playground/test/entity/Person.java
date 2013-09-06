package de.lsn.playground.test.entity;


import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@SuppressWarnings("serial")
@NamedQueries({
	@NamedQuery(name=Person.FIND_BY_ID, query="SELECT p FROM Person AS p WHERE p.id = :id")
	})
@Entity
@Table(name="Person")
public class Person implements Serializable {

	public static final String FIND_BY_ID = "Person.FIND_BY_ID";

	@Id
	private Long id = UUID.randomUUID().getMostSignificantBits();
	
	private String firstname;
	
	private String lastname;
	
	private int age;
	
	private char gender;

	public Person() {
		// TODO Auto-generated constructor stub
	}
	
	public Person(String firstname, String lastname, int age, char gender) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.gender = gender;
	}

	public Long getId() {
		return id;
	}
	
	protected void setId(Long id) {
		this.id = id;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}
	
}
