package de.lsn.playground.test.entity.dao;


import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.lsn.playground.test.entity.Person;

@Stateless
@Remote(PersonDAORemote.class)
public class PersonDAO implements PersonDAOLocal {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Person createPerson(Person p) {
		em.persist(p);
		return em.find(Person.class, p.getId());
	}

	@Override
	public Person findPersonById(Long id) {
		TypedQuery<Person> q = em.createNamedQuery(Person.FIND_BY_ID, Person.class);
		q.setParameter("id", id);
		return q.getSingleResult();
	}

	
}
