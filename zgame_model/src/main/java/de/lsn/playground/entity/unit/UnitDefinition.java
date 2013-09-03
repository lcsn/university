package de.lsn.playground.entity.unit;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.entity.attribute.Defense;
import de.lsn.playground.entity.attribute.FiringRange;
import de.lsn.playground.entity.attribute.Health;
import de.lsn.playground.entity.attribute.MovingRange;
import de.lsn.playground.entity.attribute.Offense;
import de.lsn.playground.framwork.Fraction;
import de.lsn.playground.framwork.Skill;

@SuppressWarnings("serial")
@NamedQueries({
	@NamedQuery(name=UnitDefinition.FIND_ALL, query="SELECT o FROM UnitDefinition AS o"),
	@NamedQuery(name=UnitDefinition.FIND_BY_ID, query="SELECT o FROM UnitDefinition AS o WHERE o.id = :id")
})
@Entity
public class UnitDefinition extends ZgameEntity {

	public static final String FIND_ALL = "UnitDefinition.FIND_ALL";
	public static final String FIND_BY_ID = "UnitDefinition.FIND_BY_ID";

	private String unitName = "";
	
	private Integer tier;

	@Enumerated(EnumType.STRING)
	private Fraction fraction;
	
	@Enumerated(EnumType.STRING)
	private Skill skill;

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
	private FiringRange firingRange;

	@Embedded
	@AttributeOverride(name="movingRangeValue", column=@Column(name="movingRange"))
	private MovingRange movingRange;

	private boolean vulnerableToPoison;

	private boolean vulnerableToRadiation;

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

	public Fraction getFraction() {
		return fraction;
	}

	public void setFraction(Fraction fraction) {
		this.fraction = fraction;
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

}
