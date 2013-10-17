package de.lsn.playground.entity.map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import de.lsn.playground.entity.attribute.Name;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="PLAIN")
public class Plain extends Terrain {

	public Plain() {
		this("");
	}
	
	public Plain(String nameValue) {
		super(new Name(nameValue), TerrainType.PLAIN);
	}
	
}
