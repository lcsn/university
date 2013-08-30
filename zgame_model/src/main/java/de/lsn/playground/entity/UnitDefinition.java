package de.lsn.playground.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import de.lsn.playground.framwork.Fraction;

@NamedQueries({
	@NamedQuery(name=UnitDefinition.FIND_ALL, query="SELECT o FROM UnitDefinition"),
	@NamedQuery(name=UnitDefinition.FIND_BY_ID, query="SELECT o FROM UnitDefinition WHERE o.id = :id")
})
@Entity
public class UnitDefinition extends ZgameEntity {

	public static final String FIND_ALL = "UnitDefinition.FIND_ALL";
	public static final String FIND_BY_ID = "UnitDefinition.FIND_BY_ID";

	private String tier;

	@Enumerated(EnumType.STRING)
	private Fraction fraction;

	private Long offense;

	private Long defense;

	private Long health;

	private Integer firingRange;

	private Integer movingRange;

	private boolean vulnerableToPoison;

	private boolean vulnerableToRadiation;

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public Fraction getFraction() {
		return fraction;
	}

	public void setFraction(Fraction fraction) {
		this.fraction = fraction;
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

}
