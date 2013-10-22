package de.lsn.playground.logic.game.field;

import javax.ejb.Remote;

import de.lsn.playground.entity.map.Field;
import de.lsn.playground.entity.map.FieldDefinition;
import de.lsn.playground.entity.map.Terrain;
import de.lsn.playground.framwork.exception.ZgameException;

@Remote
public interface FieldServiceDAORemote {


//	######## CREATIONAL METHODS ########
	public Terrain createTerrain(Terrain terrain) throws ZgameException;

	public FieldDefinition createFieldDefinition(FieldDefinition fieldDefinition) throws ZgameException;
	
	public Field createFieldByFieldDefinition(FieldDefinition fieldDefinition) throws ZgameException;

	public Field createFieldByFieldDefinitionId(Long fieldDefinitionId) throws ZgameException;

//	######## FINDER METHODS ########
	public Terrain findTerrainByName(String terrainName) throws ZgameException;

	public FieldDefinition findFieldDefinitionById(Long fieldDefinitionId) throws ZgameException;
	
}
