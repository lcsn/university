package de.lsn.playground.logic.game.map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import de.lsn.playground.entity.map.Map;
import de.lsn.playground.entity.map.MapDefinition;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.logic.AbstractDAO;

@Stateless
@Remote(MapServiceDAORemote.class)
public class MapServiceDAO extends AbstractDAO implements MapServiceDAOLocal {

//	######## CREATIONAL METHODS ########
	public MapDefinition createMapDefinition(MapDefinition mapDefinition) throws ZgameException {
		if(null != mapDefinition.getId()) {
			throw new ZgameException("Can not persist detached entity");
		}
		em.persist(mapDefinition);
		return em.find(MapDefinition.class, mapDefinition.getId());
	}

	public Map createMapByMapDefinition(MapDefinition mapDefinition) throws ZgameException {
		Map newMapInstance = mapDefinition.newMapInstance();
		em.persist(newMapInstance);
		return em.find(Map.class, newMapInstance.getId());
	}

	public Map createMapByMapDefinitionId(Long mapDefinitionId) throws ZgameException {
		return createMapByMapDefinition(findMapDefinitionById(mapDefinitionId));
	}

//	######## FINDER METHODS ########
	public Map findMapById(Long mapId) throws ZgameException {
		TypedQuery<Map> query = em.createNamedQuery(Map.FIND_BY_ID, Map.class);
		query.setParameter("id", mapId);
		Map map = null;
		try {
			map = query.getSingleResult();
		} catch (NoResultException | EntityNotFoundException e) {
			throw new ZgameException("Could not find " + Map.class.getSimpleName() + " with id: " + mapId, e);
		}
		
		return map;
	}
	
	public MapDefinition findMapDefinitionById(Long mapDefinitionId) throws ZgameException {
		TypedQuery<MapDefinition> query = em.createNamedQuery(MapDefinition.FIND_BY_ID, MapDefinition.class);
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
