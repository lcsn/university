package de.lsn.playground.logic.game.map;

import javax.ejb.Remote;

import de.lsn.playground.entity.map.Map;
import de.lsn.playground.entity.map.MapDefinition;
import de.lsn.playground.framwork.exception.ZgameException;

@Remote
public interface MapServiceDAORemote {
	
//	######## CREATIONAL METHODS ########
	public MapDefinition createMapDefinition(MapDefinition mapDefinition) throws ZgameException;

	public Map createMapByMapDefinition(MapDefinition mapDefinition) throws ZgameException;

	public Map createMapByMapDefinitionId(Long mapDefinitionId) throws ZgameException;

//	######## FINDER METHODS ########
	public MapDefinition findMapDefinitionById(Long mapDefinitionId) throws ZgameException;

}
