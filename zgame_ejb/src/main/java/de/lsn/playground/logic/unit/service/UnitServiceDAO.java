package de.lsn.playground.logic.service.unit;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import de.lsn.playground.entity.CampUnit;
import de.lsn.playground.entity.HiveUnit;
import de.lsn.playground.entity.Unit;
import de.lsn.playground.entity.UnitDefinition;
import de.lsn.playground.logic.AbstractDAO;
import de.lsn.playground.logic.unit.definition.UnitDefinitionDAOLocal;

@Stateless
public class UnitServiceDAO extends AbstractDAO implements UnitServiceDAOLocal {

	@EJB
	private UnitDefinitionDAOLocal unitDefinitionDAO;
	
	@Override
	public Unit createUnit(Long unitDefinitionId) {
		if (null == unitDefinitionId) {
			return null;
		}
		UnitDefinition unitDefinition = unitDefinitionDAO.findUnitDefintionById(unitDefinitionId);
		return createUnit(unitDefinition);
	}

	@Override
	public Unit createUnit(UnitDefinition unitDefinition) {
		Unit newUnit = null;
		switch (unitDefinition.getFraction()) {
		case CAMP:
			newUnit = new CampUnit();
			break;
		case HIVE:
			newUnit = new HiveUnit();
			break;
		default:
			break;
		}
		newUnit.setTier(unitDefinition.getTier());
		newUnit.setOffense(unitDefinition.getOffense());
		newUnit.setDefense(unitDefinition.getDefense());
		newUnit.setHealth(unitDefinition.getHealth());
		newUnit.setFiringRange(unitDefinition.getFiringRange());
		newUnit.setMovingRange(unitDefinition.getMovingRange());
		newUnit.setVulnerableToPoison(unitDefinition.isVulnerableToPoison());
		newUnit.setVulnerableToRadiation(unitDefinition.isVulnerableToRadiation());
		newUnit.setUnitDefinition(unitDefinition);
		return newUnit;
	}

}
