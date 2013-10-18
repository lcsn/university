package de.lsn.playground.logic.unit.definition;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import de.lsn.playground.entity.unit.UnitDefinition;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
@Remote(UnitDefinitionDAORemote.class)
public class UnitDefinitionDAO extends AbstractDAO implements UnitDefinitionDAOLocal {

//	######## CREATIONAL METHODS ########

//	######## FINDER METHODS ########
	public List<UnitDefinition> findUnitDefinitions() {
		TypedQuery<UnitDefinition> query = em.createNamedQuery(UnitDefinition.FIND_ALL, UnitDefinition.class);
		return query.getResultList();
	}

	public UnitDefinition findUnitDefintionById(Long id) {
		TypedQuery<UnitDefinition> query = em.createNamedQuery(UnitDefinition.FIND_BY_ID, UnitDefinition.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

}
