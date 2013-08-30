package de.lsn.playground.logic.unit.definition;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import de.lsn.playground.entity.UnitDefinition;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
public class UnitDefinitionDAO extends AbstractDAO implements UnitDefinitionDAOLocal {

	@SuppressWarnings("unchecked")
	@Override
	public List<UnitDefinition> findUnitDefinitions() {
		Query query = em.createNamedQuery(UnitDefinition.FIND_ALL);
		return query.getResultList();
	}

	@Override
	public UnitDefinition findUnitDefintionById(Long id) {
		Query query = em.createNamedQuery(UnitDefinition.FIND_ALL);
		query.setParameter("id", id);
		return (UnitDefinition) query.getSingleResult();
	}

}
