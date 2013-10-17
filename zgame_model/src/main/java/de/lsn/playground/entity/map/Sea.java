package de.lsn.playground.entity.map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import de.lsn.playground.entity.attribute.Name;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="SEA")
public class Sea extends Terrain {

	public Sea() {
		this("");
	}
	
	public Sea(String nameValue) {
		super(new Name(nameValue), TerrainType.SEA);
	}
	
}
