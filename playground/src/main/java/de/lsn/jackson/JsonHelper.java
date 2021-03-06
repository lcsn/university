package de.lsn.jackson;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.lsn.model.Gender;
import de.lsn.model.JsonObject;
import de.lsn.model.Person;

public class JsonHelper {

	private static JsonHelper jh;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public static JsonHelper getInstance() {
		if(null == jh) {
			jh = new JsonHelper();
		}
		return jh;
	}

	public JsonObject getObjectFromJson() {
		JsonObject o = null;
		try {
			o = mapper.readValue(FileUtils.readFileToByteArray(new File("src/main/resources/data.json")), Person.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public String getJsonFromObject(JsonObject o) {
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	public static void main(String[] args) {
		
		System.out.println("Json to Object");
		Person p = (Person) JsonHelper.getInstance().getObjectFromJson();
		System.out.println(p.toString());
		
		System.out.println("Object to Json #1");		
		System.out.println(p.toJson());
		System.out.println("Object to Json #2");
		Person p1 = new Person();
		p1.setFirstname("Freddie");
		p1.setLastname("Mercury");
		p1.setAge(0);
		p1.setGender(Gender.MALE);
		System.out.println(p1.toJson());
	}
	
}
