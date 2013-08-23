package de.lsn.model;

import de.lsn.jackson.JsonHelper;

public class Person implements JsonObject {

	private String firstname;
	
	private String lastname;
	
	private int age;
	
	private Gender gender;

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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return firstname+", "+lastname+", "+gender+", "+age;
	}
	
	public Class<? extends JsonObject> getTargetClass() {
		return this.getClass();
	}
	
	public String toJson() {
		return JsonHelper.getInstance().getJsonFromObject(this);
	}
	
}
