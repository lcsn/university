package de.lsn.playground.logic.unit.service;

import javax.ejb.Remote;

import de.lsn.playground.entity.unit.Unit;
import de.lsn.playground.entity.unit.UnitDefinition;

@Remote
public interface UnitServiceDAORemote {

//	######## CREATIONAL METHODS ########
	public Unit createUnitByUnitDefinitionId(Long unitDefinitionId);
	
	public Unit createByUnitDefinition(UnitDefinition unitDefinition);
	
//	######## FINDER METHODS ########

}
