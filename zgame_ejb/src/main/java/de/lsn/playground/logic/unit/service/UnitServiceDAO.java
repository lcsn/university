package de.lsn.playground.logic.unit.service;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.lsn.playground.entity.unit.CampUnit;
import de.lsn.playground.entity.unit.HiveUnit;
import de.lsn.playground.entity.unit.Unit;
import de.lsn.playground.entity.unit.UnitDefinition;
import de.lsn.playground.logic.AbstractDAO;
import de.lsn.playground.logic.unit.definition.UnitDefinitionDAOLocal;

@Stateless
@Remote(UnitServiceDAORemote.class)
public class UnitServiceDAO extends AbstractDAO implements UnitServiceDAOLocal {

	@EJB
	private UnitDefinitionDAOLocal unitDefinitionDAO;
	
//	######## CREATIONAL METHODS ########
	public Unit createUnitByUnitDefinitionId(Long unitDefinitionId) {
		if (null == unitDefinitionId) {
			return null;
		}
		UnitDefinition unitDefinition = unitDefinitionDAO.findUnitDefintionById(unitDefinitionId);
		return createByUnitDefinition(unitDefinition);
	}

	public Unit createByUnitDefinition(UnitDefinition unitDefinition) {
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
		newUnit.setUnitName(unitDefinition.getUnitName());
		newUnit.setSkill(unitDefinition.getSkill());
		newUnit.setTier(unitDefinition.getTier());
		newUnit.setOffense(unitDefinition.getOffense());
		newUnit.setDefense(unitDefinition.getDefense());
		newUnit.setHealth(unitDefinition.getHealth());
		newUnit.setFiringRange(unitDefinition.getFiringRange());
		newUnit.setMovingRange(unitDefinition.getMovingRange());
		newUnit.setVulnerableToPoison(unitDefinition.isVulnerableToPoison());
		newUnit.setVulnerableToRadiation(unitDefinition.isVulnerableToRadiation());
		newUnit.setUnitDefinition(unitDefinition);
		
		em.persist(newUnit);
		
		return newUnit;
	}

//	######## FINDER METHODS ########
	
}
