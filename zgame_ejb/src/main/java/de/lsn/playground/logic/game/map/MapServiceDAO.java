package de.lsn.playground.logic.game.map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import de.lsn.playground.entity.map.FieldDefinition;
import de.lsn.playground.entity.map.MapDefinition;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
@Remote(MapServiceDAORemote.class)
public class MapServiceDAO extends AbstractDAO implements MapServiceDAOLocal {

//	######## CREATIONAL METHODS ########
	public void createMapDefinition(MapDefinition mapDefinition) throws ZgameException {
		if(null != mapDefinition.getId()) {
			throw new ZgameException("Can not persist detached entity");
		}
		em.persist(mapDefinition);
	}

	public void createMapByMapDefinition(MapDefinition mapDefinition) throws ZgameException {
		em.persist(mapDefinition.newMapInstance());
	}

	public void createMapByMapDefinitionId(Long mapDefinitionId) throws ZgameException {
		createMapByMapDefinition(findMapDefinitionById(mapDefinitionId));
	}

//	######## FINDER METHODS ########
	public MapDefinition findMapDefinitionById(Long mapDefinitionId) throws ZgameException {
		TypedQuery<MapDefinition> query = em.createNamedQuery(FieldDefinition.FIND_BY_ID, MapDefinition.class);
		query.setParameter("id", mapDefinitionId);
		MapDefinition mapDefinition = null;
		try {
			mapDefinition = query.getSingleResult();
		} catch (NoResultException | EntityNotFoundException e) {
			throw new ZgameException("Could not find " + MapDefinition.class.getSimpleName() + " with id: " + mapDefinitionId, e);
		}
		
		return mapDefinition;
	}
	
}
