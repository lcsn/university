package de.lsn.playground.logic.game.map;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import de.lsn.playground.entity.map.Map;
import de.lsn.playground.entity.map.MapDefinition;
import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.logic.AbstractDAO;
import de.lsn.playground.logic.game.ZgameServiceDAOLocal;
import de.lsn.playground.logic.player.PlayerServiceDAOLocal;

@Stateless
@Remote(MapServiceDAORemote.class)
public class MapServiceDAO extends AbstractDAO implements MapServiceDAOLocal {

	@EJB
	protected ZgameServiceDAOLocal zgameService;
	
	@EJB
	protected PlayerServiceDAOLocal playerService;
	
//	######## MISC METHODS ########
	
	public void addPlayer(Long mapId, String username, String password) throws ZgameException {
		Map map = findMapById(mapId);
		map.addPlayerToPlayerSlot(playerService.findPlayerByUsernameAndPassword(username, password));
		zgameService.updateZgameEntity(map);
	}
	
	public void removePlayer(Long mapId, String username, String password) throws ZgameException {
		Map map = findMapById(mapId);
		map.removePlayerFromPlayerSlot(playerService.findPlayerByUsernameAndPassword(username, password));
		zgameService.updateZgameEntity(map);
	}
	
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
	
	public List<Map> findMapsByPlayer(String username, String password) throws ZgameException {
		List<Map> maps = null;
		if(playerService.authenticate(username, password)) {
			Player player = playerService.findPlayerByUsernameAndPassword(username, password);
			TypedQuery<Map> query = em.createNamedQuery(Map.FIND_BY_PLAYER_ID, Map.class);
			query.setParameter("playerId", player.getId());
			try {
				maps = query.getResultList();
			} catch (NoResultException | EntityNotFoundException e) {
				throw new ZgameException("Could not find any " + Map.class.getSimpleName() + " with id: " + player.getId(), e);
			}
		}
		return maps;
	}
	
}
