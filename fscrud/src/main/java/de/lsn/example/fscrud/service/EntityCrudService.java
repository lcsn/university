package de.lsn.example.fscrud.service;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.junit.Test;

import de.lsn.example.fscrud.model.CrudEntity;

public class EntityCrudService implements CrudService<CrudEntity> {
	
	@Inject
	private Logger log;

	public CrudEntity loadAll(Class<CrudEntity> classOfT) {
		log.info("NOT YET IMPLEMENTED!");
		return null;
	}
	
	public CrudEntity load(Class<CrudEntity> classOfT, Long id) {
		log.info("NOT YET IMPLEMENTED!");
		return null;
	}

	public CrudEntity save(CrudEntity t) {
		log.info("NOT YET IMPLEMENTED!");
		return null;
	}

	public void test() {
		log.info("Test succeeded!");
	}
}
