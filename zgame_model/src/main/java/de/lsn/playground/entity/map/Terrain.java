package de.lsn.playground.entity.map;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.entity.attribute.Name;

@SuppressWarnings("serial")
@Entity
@Table(name="Terrain")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="terrainType", discriminatorType=DiscriminatorType.STRING, length=12)
public class Terrain extends ZgameEntity {

	@Embedded
	@AttributeOverride(name="nameValue", column=@Column(name="terrainName"))
	private Name terrainName = new Name("Empty Terrain");
	
	@Enumerated(EnumType.STRING)
	private TerrainType terrainType = TerrainType.NONE;
	
	public Terrain() {
		// TODO Auto-generated constructor stub
	}

	public Terrain(Name terrainName, TerrainType terrainType) {
		this.terrainName = terrainName;
		this.terrainType = terrainType;
	}

	public Name getTerrainName() {
		return terrainName;
	}
	
	protected void setTerrainName(Name terrainName) {
		this.terrainName = terrainName;
	}
	
	public TerrainType getTerrainType() {
		return terrainType;
	}
	
	protected void setTerrainType(TerrainType terrainType) {
		this.terrainType = terrainType;
	}
	
}
