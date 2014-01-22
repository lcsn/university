package de.lsn.model;

import java.util.Calendar;

import de.lsn.resource.Constants;
import de.lsn.resource.helper.DateConvenience;

public class Age {

	private Calendar birthday;
	
	private int age;

	public Age() {
		// TODO Auto-generated constructor stub
	}
	
	public Age(String birthday) {
		this(DateConvenience.dateStringToCalendar(birthday), DateConvenience.yearsSinceGivenDate(birthday));
	}
	
	public Age(String birthday, int age) {
		this(DateConvenience.dateStringToCalendar(birthday), age);
	}
	
	public Age(Calendar birthday, int age) {
		super();
		if (Calendar.getInstance().before(birthday)) {
			System.err.println("Birthday lays in the future");
		}
		this.birthday = birthday;
		this.age = age;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return age+", "+Constants.SDF_DE.format(birthday.getTime());
	}
	
}
