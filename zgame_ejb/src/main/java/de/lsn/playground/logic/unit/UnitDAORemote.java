package de.lsn.playground.logic.unit;

import java.util.List;

import javax.ejb.Remote;

import de.lsn.playground.entity.unit.Unit;
import de.lsn.playground.framwork.exception.ZgameException;

@Remote
public interface UnitDAORemote {

//	######## CREATIONAL METHODS ########
	public Unit createUnit(Unit unit) throws ZgameException;

//	######## FINDER METHODS ########
	public List<Unit> findUnits();

	public Unit findUnitById(Long id);

}
