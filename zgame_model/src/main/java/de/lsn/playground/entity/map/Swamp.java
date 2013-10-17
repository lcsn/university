package de.lsn.playground.entity.map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import de.lsn.playground.entity.attribute.Name;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="SWAMP")
public class Swamp extends Terrain {

	public Swamp() {
		this("");
	}
	
	public Swamp(String nameValue) {
		super(new Name(nameValue), TerrainType.SWAMP);
	}
	
}
