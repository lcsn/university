package de.lsn.playground.logic.unit.definition;

import java.util.List;

import javax.ejb.Remote;

import de.lsn.playground.entity.unit.UnitDefinition;

@Remote
public interface UnitDefinitionDAORemote {

	public List<UnitDefinition> findUnitDefinitions();
	
	public UnitDefinition findUnitDefintionById(Long id);
	
}
