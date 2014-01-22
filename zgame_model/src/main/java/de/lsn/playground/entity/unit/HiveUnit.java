package de.lsn.playground.entity.unit;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.lsn.playground.framwork.Fraction;

@SuppressWarnings("serial")
@Entity
//@Table(name="HiveUnit")
@DiscriminatorValue(value="HIVE")
public class HiveUnit extends Unit {

	/*
	@Column(name="FRACTION")
	@Enumerated(EnumType.STRING)
	private Fraction fraction = Fraction.HIVE;
	
	public Fraction getFraction() {
		return fraction;
	}
	
	protected void setFraction(Fraction fraction) {
		this.fraction = fraction;
	}
	*/
	
}
