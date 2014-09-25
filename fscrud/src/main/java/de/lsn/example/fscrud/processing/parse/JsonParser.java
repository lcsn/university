package de.lsn.example.fscrud.processing.parse;

import java.util.logging.Logger;

import javax.inject.Inject;

import de.lsn.example.fscrud.model.CrudEntity;

public class JsonParser {

	@Inject
	private static Logger log;
	
	public static <T> T toObject(String json, Class<T> classOfT) {
		log.info("NOT YET IMPLEMENTED!");
		return null;
	}
	
	public static String toJson(CrudEntity entity) {
		log.info("NOT YET IMPLEMENTED!");
		return "";
	}
	
}
