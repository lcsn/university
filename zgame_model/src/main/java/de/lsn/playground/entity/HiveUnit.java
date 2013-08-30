package de.lsn.playground.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.lsn.playground.framwork.Fraction;

@Entity
public class HiveUnit extends Unit {

	@Enumerated(EnumType.STRING)
	private Fraction fraction = Fraction.HIVE;
	
	public Fraction getFraction() {
		return fraction;
	}
	
	protected void setFraction(Fraction fraction) {
		this.fraction = fraction;
	}
	
}
