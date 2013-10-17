package de.lsn.playground.entity.map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.entity.attribute.Coord;
import de.lsn.playground.entity.unit.UnitDefinition;


@SuppressWarnings("serial")
@Entity
@Table(name="FieldDefinition")
public class FieldDefinition extends ZgameEntity {

	private String fieldDescription;
		
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name="xCoord", column=@Column(name="xcoord")),
			@AttributeOverride(name="yCoord", column=@Column(name="ycoord"))
	})
	private Coord coord = new Coord(0, 0);
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "terrainId")
	private Terrain terrain;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unitDefinitionId")
	private UnitDefinition unitDefinition;
	
	private boolean radiation = false;
	
	private boolean poison = false;

	public String getFieldDescription() {
		return fieldDescription;
	}
	
	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	protected void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}
	
	public UnitDefinition getUnitDefinition() {
		return unitDefinition;
	}
	
	protected void setUnitDefinition(UnitDefinition unitDefinition) {
		this.unitDefinition = unitDefinition;
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
	
}
