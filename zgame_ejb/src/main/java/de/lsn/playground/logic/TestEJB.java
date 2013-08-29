package de.lsn.playground.logic;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateless
public class TestEJB implements TestEJBLocal, TestEJBRemote {

	private static Logger log = LogManager.getLogger(TestEJB.class.getName());
	
	@PersistenceContext(unitName="zgame")
	private EntityManager em;
	
	@Override
	public void moo() {
		log.entry();
		log.debug("moo1");
	}

}
