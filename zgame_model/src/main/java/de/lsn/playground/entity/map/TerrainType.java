package de.lsn.playground.entity.map;

public enum TerrainType {

	PLAIN,
	WOOD,
	SEA,
	HILL,
	SWAMP,
	DESERT,
	NONE;

	public String toString() {
		return this.name().toUpperCase();
	}
	
}
