package de.lsn.playground.logic.unit.definition;

import java.util.List;

import javax.ejb.Remote;

import de.lsn.playground.entity.unit.UnitDefinition;
import de.lsn.playground.framwork.exception.ZgameException;

@Remote
public interface UnitDefinitionDAORemote {

//	######## CREATIONAL METHODS ########
	public UnitDefinition createUnitDefinition(UnitDefinition unitDefinition) throws ZgameException;

//	######## FINDER METHODS ########
	
	public List<UnitDefinition> findUnitDefinitions();
	
	public UnitDefinition findUnitDefintionById(Long id);
	
}
