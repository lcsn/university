package de.lsn.playground.json;

public interface JsonObject {

	public Class<? extends JsonObject> getTargetClass();
	
	public String toJson();
	
}
