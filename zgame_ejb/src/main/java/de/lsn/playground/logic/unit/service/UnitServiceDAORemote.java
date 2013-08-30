package de.lsn.playground.logic.unit.service;

import javax.ejb.Remote;

import de.lsn.playground.entity.Unit;
import de.lsn.playground.entity.UnitDefinition;

@Remote
public interface UnitServiceDAORemote {

	public Unit createUnit(Long unitDefinitionId);
	
	public Unit createUnit(UnitDefinition unitDefinition);
	
}
