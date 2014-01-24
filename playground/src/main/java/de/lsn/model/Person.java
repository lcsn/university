package de.lsn.model;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.lsn.jackson.JsonHelper;

public class Person extends AbstractEntity implements JsonObject {

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
	
	@JsonIgnore
	public Class<? extends JsonObject> getTargetClass() {
		return this.getClass();
	}
	
	@Override
	public String toJson() {
		return JsonHelper.getInstance().getJsonFromObject(this);
	}

	@Override
	public HashMap<String,Object> toMap() {
		return JsonHelper.getInstance().getMapFromObject(this);
	}
	
}
