package de.lsn.playground.logic.game.map;

import javax.ejb.Remote;

import de.lsn.playground.entity.map.MapDefinition;
import de.lsn.playground.framwork.exception.ZgameException;

@Remote
public interface MapServiceDAORemote {
	
	public void createMapDefinition(MapDefinition mapDefinition) throws ZgameException;

	public void createMapByMapDefinition(MapDefinition mapDefinition) throws ZgameException;

	public void createMapByMapDefinitionId(Long mapDefinitionId) throws ZgameException;

	public MapDefinition findMapDefinitionById(Long mapDefinitionId) throws ZgameException;

}
