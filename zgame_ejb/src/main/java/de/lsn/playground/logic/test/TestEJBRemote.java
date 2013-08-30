package de.lsn.playground.logic.test;

import javax.ejb.Remote;

@Remote
public interface TestEJBRemote {

	public void moo();
	
	public void testPersistence(int n);
	
}
