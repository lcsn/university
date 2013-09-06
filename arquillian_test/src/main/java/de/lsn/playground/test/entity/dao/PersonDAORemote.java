package de.lsn.playground.test.entity.dao;


import javax.ejb.Remote;

import de.lsn.playground.test.entity.Person;

@Remote
public interface PersonDAORemote {
	
	public Person createPerson(Person p);

	public Person findPersonById(Long id);
	
}
