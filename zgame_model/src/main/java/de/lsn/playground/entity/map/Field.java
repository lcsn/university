package de.lsn.playground.entity.map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.entity.attribute.Coord;


@SuppressWarnings("serial")
@Entity
@Table(name="Field")
public class Field extends ZgameEntity {

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name="xCoord", column=@Column(name="xcoord")),
			@AttributeOverride(name="yCoord", column=@Column(name="ycoord"))
	})
	private Coord coord = new Coord(0, 0);
	
	@ManyToOne
	@JoinColumn(name = "terrainId")
	private Terrain terrain;
	
	private boolean radiation = false;
	
	private boolean poison = false;

	@ManyToOne
	@JoinColumn(name = "fieldDefinitionId")
	private FieldDefinition fieldDefinition;
	
	public Coord getCoord() {
		return coord;
	}
	
	protected void setCoord(Coord coord) {
		this.coord = coord;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	protected void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}
	
	public boolean isRadiation() {
		return radiation;
	}
	
	protected void setRadiation(boolean radiation) {
		this.radiation = radiation;
	}
	
	public boolean isPoison() {
		return poison;
	}
	
	protected void setPoison(boolean poison) {
		this.poison = poison;
	}

	public FieldDefinition getFieldDefinition() {
		return fieldDefinition;
	}
	
	protected void setFieldDefinition(FieldDefinition fieldDefinition) {
		this.fieldDefinition = fieldDefinition;
	}
	
}
