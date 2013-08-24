package de.lsn.model;

import de.lsn.jackson.JsonHelper;

public class Person implements JsonObject {

	private Name name;
	
	private Age age;
	
	private Gender gender;

	public Name getName() {
		return name;
	}
	
	public void setName(Name name) {
		this.name = name;
	}
	
	public Age getAge() {
		return age;
	}
	
	public void setAge(Age age) {
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
		return name.toString();
	}
	
	public Class<? extends JsonObject> getTargetClass() {
		return this.getClass();
	}
	
	public String toJson() {
		return JsonHelper.getInstance().getJsonFromObject(this);
	}
	
}
