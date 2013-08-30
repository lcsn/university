package de.lsn.playground.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import de.lsn.playground.framwork.Fraction;

@SuppressWarnings("serial")
@Entity
@Table(name="CampUnit")
@DiscriminatorValue(value="CAMP")
public class CampUnit extends Unit {

	@Column(name="FRACTION")
	@Enumerated(EnumType.STRING)
	private Fraction fraction = Fraction.CAMP;
	
	public Fraction getFraction() {
		return fraction;
	}
	
	protected void setFraction(Fraction fraction) {
		this.fraction = fraction;
	}
	
}
