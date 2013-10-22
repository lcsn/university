package de.lsn.playground.entity.map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import de.lsn.playground.entity.attribute.Name;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="HILL")
public class Hill extends Terrain {

	public Hill() {
		this("");
	}
	
	public Hill(String nameValue) {
		super(new Name(nameValue), TerrainType.HILL);
	}
	
}
