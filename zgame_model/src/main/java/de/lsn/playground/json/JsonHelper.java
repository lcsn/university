package de.lsn.playground.json;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

	private static JsonHelper jh;
	
	private ObjectMapper mapper;
	
	public JsonHelper() {
		this.mapper = new ObjectMapper(new JsonFactory());
	}
	
	public static JsonHelper getInstance() {
		if(null == jh) {
			jh = new JsonHelper();
		}
		return jh;
	}
	public <T> JsonObject getObjectFromJson() {
		return getObjectFromJson(JsonObject.class);
	}
		
	public <T> T getObjectFromJson(Class<T> clazz) {
		T t = null;
		try {
			String data = FileUtils.readFileToString(new File("src/main/resources/data.json"), Charset.forName("UTF-8"));
			t = getObjectFromJson(data.getBytes(), clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public JsonObject getObjectFromJson(byte[] byteArray) {
		return getObjectFromJson(byteArray, JsonObject.class);
	}
	
	public HashMap<String, Object> getMapFromObject(JsonObject o) {
		TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>(){};
		HashMap<String,Object> propertyMap = null;
        try {
			propertyMap = mapper.readValue(getJsonFromObject(o), typeRef);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return propertyMap;
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
