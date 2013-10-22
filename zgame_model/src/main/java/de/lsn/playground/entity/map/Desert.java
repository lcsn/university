package de.lsn.playground.entity.map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import de.lsn.playground.entity.attribute.Name;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="DESERT")
public class Desert extends Terrain {

	public Desert() {
		this("");
	}
	
	public Desert(String nameValue) {
		super(new Name(nameValue), TerrainType.DESERT);
	}
	
}
