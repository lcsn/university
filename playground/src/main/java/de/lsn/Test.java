package de.lsn;

import de.lsn.http.HttpConnector;
import de.lsn.jackson.JsonHelper;
import de.lsn.model.Age;
import de.lsn.model.Gender;
import de.lsn.model.Name;
import de.lsn.model.Person;

public class Test {

	public static void main(String[] args) {
		testJsonHelper();
		
		testHttpConnector();
	}
	
	private static void testHttpConnector() {
		HttpConnector.getInstance().get("https://boiii.firebaseio.com/users/.json");
	}
	
	private static void testJsonHelper() {
		System.out.println("Json to Object");
		Person p = (Person) JsonHelper.getInstance().getObjectFromJson();
		System.out.println(p.toString());
		
		System.out.println("Object to Json #1");		
		System.out.println(p.toJson());
		System.out.println("Object to Json #2");
		Person p1 = new Person();
		p1.setName(new Name("Freddie", "Mercury"));
		p1.setAge(new Age("01.04.1990"));
		p1.setGender(Gender.MALE);
		System.out.println(p1.toJson());
	}
	
}
