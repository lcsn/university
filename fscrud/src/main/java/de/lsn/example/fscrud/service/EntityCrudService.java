package de.lsn.example.fscrud.service;

import java.util.logging.Logger;

import de.lsn.example.fscrud.model.CrudEntity;

public abstract class EntityCrudService implements CrudService<CrudEntity> {
	
	private Logger log = Logger.getLogger(EntityCrudService.class.getSimpleName());

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
