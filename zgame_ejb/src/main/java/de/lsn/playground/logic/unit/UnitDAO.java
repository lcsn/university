package de.lsn.playground.logic.unit;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import de.lsn.playground.entity.unit.Unit;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
@Remote(UnitDAORemote.class)
public class UnitDAO extends AbstractDAO implements UnitDAOLocal {

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> findUnits() {
		Query query = em.createNamedQuery(Unit.FIND_ALL);
		return query.getResultList();
	}

	@Override
	public Unit findUnitById(Long id) {
		Query query = em.createNamedQuery(Unit.FIND_BY_ID);
		query.setParameter("id", id);
		return (Unit) query.getSingleResult();
	}
	
}
