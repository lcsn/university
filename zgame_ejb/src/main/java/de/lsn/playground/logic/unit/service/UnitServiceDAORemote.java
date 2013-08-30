package de.lsn.playground.logic.unit.service;

import javax.ejb.Remote;

import de.lsn.playground.entity.unit.Unit;
import de.lsn.playground.entity.unit.UnitDefinition;

@Remote
public interface UnitServiceDAORemote {

	public Unit createUnit(Long unitDefinitionId);
	
	public Unit createUnit(UnitDefinition unitDefinition);
	
}
