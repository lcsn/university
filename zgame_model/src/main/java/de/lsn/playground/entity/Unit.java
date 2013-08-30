package de.lsn.playground.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
	@NamedQuery(name=UnitDefinition.FIND_ALL, query="SELECT o FROM Unit"),
	@NamedQuery(name=UnitDefinition.FIND_BY_ID, query="SELECT o FROM Unit WHERE o.id = :id")
})
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Unit extends ZgameEntity {

	public static final String FIND_ALL = "Unit.FIND_ALL";
	public static final String FIND_BY_ID = "Unit.FIND_BY_ID";

	private String tier;
	
	private Long offense = 0l;
	
	private Long defense = 0l;
	
//	private Long offense_gun;
//	private Long offense_melee;
//	private Long defense_gun;
//	private Long defense_melee;
	
	private Long health = 0l;
	
	private Integer firingRange = 0;
	
	private Integer movingRange = 0;
	
	private boolean vulnerableToPoison;
	
	private boolean vulnerableToRadiation;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unitDefinitionId")
	private UnitDefinition unitDefinition;
	
	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public Long getOffense() {
		return offense;
	}

	public void setOffense(Long offense) {
		this.offense = offense;
	}

	public Long getDefense() {
		return defense;
	}

	public void setDefense(Long defense) {
		this.defense = defense;
	}

	public Long getHealth() {
		return health;
	}

	public void setHealth(Long health) {
		this.health = health;
	}

	public Integer getFiringRange() {
		return firingRange;
	}

	public void setFiringRange(Integer firingRange) {
		this.firingRange = firingRange;
	}

	public Integer getMovingRange() {
		return movingRange;
	}

	public void setMovingRange(Integer movingRange) {
		this.movingRange = movingRange;
	}

	public boolean isVulnerableToPoison() {
		return vulnerableToPoison;
	}

	public void setVulnerableToPoison(boolean vulnerableToPoison) {
		this.vulnerableToPoison = vulnerableToPoison;
	}

	public boolean isVulnerableToRadiation() {
		return vulnerableToRadiation;
	}

	public void setVulnerableToRadiation(boolean vulnerableToRadiation) {
		this.vulnerableToRadiation = vulnerableToRadiation;
	}
	
	public UnitDefinition getUnitDefinition() {
		return unitDefinition;
	}
	
	public void setUnitDefinition(UnitDefinition unitDefinition) {
		this.unitDefinition = unitDefinition;
	}
	
}
