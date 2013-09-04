package de.lsn.playground.json;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.lsn.playground.entity.ZgameEntity;

public class JsonHelper {

	private static JsonHelper jh;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public static JsonHelper getInstance() {
		if(null == jh) {
			jh = new JsonHelper();
		}
		return jh;
	}

	public <T> T getObjectFromJson(Class<T> clazz) {
		byte[] byteArray;
		T t = null;
		try {
			byteArray = FileUtils.readFileToByteArray(new File("src/main/resources/data.json"));
			t = getObjectFromJson(byteArray, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	

	public <T> T getObjectFromJson(byte[] byteArray, Class<T> clazz) {
		T t = null;
		try {
			t = (T) mapper.readValue(byteArray, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
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
