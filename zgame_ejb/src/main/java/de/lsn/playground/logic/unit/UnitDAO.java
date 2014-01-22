package de.lsn.playground.logic.unit;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;

import de.lsn.playground.entity.unit.Unit;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
@Remote(UnitDAORemote.class)
public class UnitDAO extends AbstractDAO implements UnitDAOLocal {

	
//	######## CREATIONAL METHODS ########
	public Unit createUnit(Unit unit) throws ZgameException {
		if(null != unit.getId()) {
			throw new ZgameException("Can not persist detached entity");
		}
		em.persist(unit);
		return em.find(Unit.class, unit.getId());
	}	
	
//	######## FINDER METHODS ########
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
