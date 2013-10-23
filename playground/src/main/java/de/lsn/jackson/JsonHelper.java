/*
package de.lsn.jackson;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.lsn.model.Person;
import de.lsn.playground.json.JsonObject;

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
		byte[] byteArray;
		JsonObject o = null;
		try {
			byteArray = FileUtils.readFileToByteArray(new File("src/main/resources/data.json"));
			o = getObjectFromJson(byteArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return o;
	}
	

	public JsonObject getObjectFromJson(byte[] byteArray) {
		JsonObject o = null;
		try {
			o = mapper.readValue(byteArray, Person.class);
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

}
*/