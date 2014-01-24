package de.lsn.playground.json;

import java.util.HashMap;

public interface JsonObject {

	public Class<? extends JsonObject> getTargetClass();
	
	public String toJson();

	public HashMap<String, Object> toMap();
	
}
