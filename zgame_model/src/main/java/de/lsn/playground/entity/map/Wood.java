package de.lsn.playground.entity.map;

import javax.persistence.DiscriminatorValue;

import de.lsn.playground.entity.attribute.Name;

@SuppressWarnings("serial")
@DiscriminatorValue(value="WOOD")
public class Wood extends Terrain {

	public Wood() {
		this("");
	}
	
	public Wood(String nameValue) {
		super(new Name(nameValue), TerrainType.WOOD);
	}
	
}
