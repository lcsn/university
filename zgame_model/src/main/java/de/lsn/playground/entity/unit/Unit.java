package de.lsn.playground.entity.unit;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.framwork.Experience;
import de.lsn.playground.framwork.Skill;
import de.lsn.playground.framwork.attribute.Defense;
import de.lsn.playground.framwork.attribute.FiringRange;
import de.lsn.playground.framwork.attribute.Health;
import de.lsn.playground.framwork.attribute.MovingRange;
import de.lsn.playground.framwork.attribute.Offense;

@SuppressWarnings("serial")
@NamedQueries({
	@NamedQuery(name=Unit.FIND_ALL, query="SELECT o FROM Unit AS o"),
	@NamedQuery(name=Unit.FIND_BY_ID, query="SELECT o FROM Unit AS o WHERE o.id = :id")
})
@Entity
@Table(name="Unit")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="FRACTION", discriminatorType=DiscriminatorType.STRING, length=12)
public class Unit extends ZgameEntity {

	public static final String FIND_ALL = "Unit.FIND_ALL";
	public static final String FIND_BY_ID = "Unit.FIND_BY_ID";

	private String unitName = "";
	
	private Integer tier;
	
	@Enumerated(EnumType.STRING)
	private Experience experience = Experience.LOW;

	@Enumerated(EnumType.STRING)
	private Skill skill;
	
//	private Long offense_gun;
//	private Long offense_melee;
//	private Long defense_gun;
//	private Long defense_melee;

	@Embedded
	@AttributeOverride(name="offenseValue", column=@Column(name="offense"))
	private Offense offense;

	@Embedded
	@AttributeOverride(name="defenseValue", column=@Column(name="defense"))
	private Defense defense;

	@Embedded
	@AttributeOverride(name="healthPoints", column=@Column(name="health"))
	private Health health;

	@Embedded
	@AttributeOverride(name="firingRangeValue", column=@Column(name="firingRange"))
	private FiringRange firingRange = new FiringRange(0);

	@Embedded
	@AttributeOverride(name="movingRangeValue", column=@Column(name="movingRange"))
	private MovingRange movingRange = new MovingRange(0);
	
	private boolean vulnerableToPoison;
	
	private boolean vulnerableToRadiation;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unitDefinitionId")
	private UnitDefinition unitDefinition;
	
	public String getUnitName() {
		return unitName;
	}
	
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public Integer getTier() {
		return tier;
	}

	public void setTier(Integer tier) {
		this.tier = tier;
	}

	public Experience getExperience() {
		return experience;
	}
	
	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	
	public Skill getSkill() {
		return skill;
	}
	
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
	public Offense getOffense() {
		return offense;
	}

	public void setOffense(Offense offense) {
		this.offense = offense;
	}

	public Defense getDefense() {
		return defense;
	}

	public void setDefense(Defense defense) {
		this.defense = defense;
	}

	public Health getHealth() {
		return health;
	}

	public void setHealth(Health health) {
		this.health = health;
	}

	public FiringRange getFiringRange() {
		return firingRange;
	}

	public void setFiringRange(FiringRange firingRange) {
		this.firingRange = firingRange;
	}

	public MovingRange getMovingRange() {
		return movingRange;
	}

	public void setMovingRange(MovingRange movingRange) {
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
	
	@Override
	public String toString() {
		return " Name/Health/Offense/Defense : "+unitDefinition.getUnitName()+"/"+health.getHealthPoints()+"/"+offense.getOffenseValue()+"/"+defense.getDefenseValue();
	}
	
}
