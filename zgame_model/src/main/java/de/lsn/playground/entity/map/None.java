package de.lsn.playground.entity.map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import de.lsn.playground.entity.attribute.Name;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="NONE")
public class None extends Terrain {

	public None() {
		this("");
	}
	
	public None(String nameValue) {
		super(new Name(nameValue), TerrainType.NONE);
	}
	
}
