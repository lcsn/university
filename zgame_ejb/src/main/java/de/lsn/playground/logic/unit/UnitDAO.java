package de.lsn.playground.logic.unit;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import de.lsn.playground.entity.Unit;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
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
