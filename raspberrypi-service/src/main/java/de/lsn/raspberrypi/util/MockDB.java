package de.lsn.raspberrypi.util;

import java.util.ArrayList;
import java.util.List;

import de.lsn.raspberrypi.model.Person;

public class MockDB {

	public static List<Person> persons = new ArrayList<Person>();
	static {
		persons.add(new Person(1l, "Lars", "Simon"));
		persons.add(new Person(2l, "Foo", "Moo"));
	}
	
}
