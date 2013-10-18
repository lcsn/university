package de.lsn.playground.logic.game.field;

import javax.ejb.Remote;

import de.lsn.playground.entity.map.FieldDefinition;
import de.lsn.playground.entity.map.Terrain;
import de.lsn.playground.framwork.exception.ZgameException;

@Remote
public interface FieldServiceDAORemote {


//	######## CREATIONAL METHODS ########
	public void createTerrain(Terrain terrain) throws ZgameException;

	public void createFieldDefinition(FieldDefinition fieldDefinition) throws ZgameException;
	
	public void createFieldByFieldDefinition(FieldDefinition fieldDefinition) throws ZgameException;

	public void createFieldByFieldDefinitionId(Long fieldDefinitionId) throws ZgameException;

//	######## FINDER METHODS ########
	public FieldDefinition findFieldDefinitionById(Long fieldDefinitionId) throws ZgameException;
	
}
