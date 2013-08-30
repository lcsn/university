package de.lsn.playground.logic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDAO {

	@PersistenceContext
	protected EntityManager em;
	
}
