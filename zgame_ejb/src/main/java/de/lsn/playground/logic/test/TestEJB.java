package de.lsn.playground.logic.test;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.lsn.playground.entity.Test;

@Stateless
public class TestEJB implements TestEJBLocal, TestEJBRemote {

//	private static Logger log = LogManager.getLogger(TestEJB.class.getName());
	
	@PersistenceContext(unitName="zgame")
	private EntityManager em;
	
	@Override
	public void moo() {
//		log.entry();
//		log.debug("moo1");
	}

	@Override
	public void testPersistence(int n) {
		Test t;
		for (int i = 0; i < n; i++) {
			t = new Test();
			em.persist(t);
		}
	}
	

}
