package de.lsn.playground.logic.unit;

import java.util.List;

import javax.ejb.Remote;

import de.lsn.playground.entity.unit.Unit;

@Remote
public interface UnitDAORemote {

	public List<Unit> findUnits();

	public Unit findUnitById(Long id);

}
