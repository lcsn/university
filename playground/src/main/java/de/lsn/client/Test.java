package de.lsn.client;

import de.lsn.http.HttpConnector;
import de.lsn.jackson.JsonHelper;
import de.lsn.model.Age;
import de.lsn.model.Gender;
import de.lsn.model.Name;
import de.lsn.model.Person;

public class Test {

	public static void main(String[] args) {
//		testJsonHelper();
		
		testHttpConnector();
	}
	
	private static void testHttpConnector() {
//		HttpConnector.getInstance().get("https://boiii.firebaseio.com/.json");
		
		Person p1 = new Person();
		p1.setName(new Name("Ano", "Nymous"));
		p1.setAge(new Age("01.04.1992"));
		p1.setGender(Gender.MALE);
		System.out.println(p1.toJson());
		
		HttpConnector.getInstance().put("https://boiii.firebaseio.com/person/"+p1.getId()+"/.json", p1);
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
