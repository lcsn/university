package de.lsn.playground.entity.map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.entity.attribute.Coord;
import de.lsn.playground.entity.unit.UnitDefinition;
import de.lsn.playground.framwork.exception.ZgameException;


@SuppressWarnings("serial")
@NamedQueries({
	@NamedQuery(name=FieldDefinition.FIND_BY_ID, query="SELECT o FROM FieldDefinition AS o WHERE o.id = :id")
})
@Entity
@Table(name="FieldDefinition")
public class FieldDefinition extends ZgameEntity {

	public static final String FIND_BY_ID = "FieldDefinition.FIND_BY_ID";

	private String fieldDescription;
		
	@NotNull
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name="xCoord", column=@Column(name="xcoord")),
			@AttributeOverride(name="yCoord", column=@Column(name="ycoord"))
	})
	private Coord coord = new Coord(0, 0);
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "terrainId")
	private Terrain terrain;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unitDefinitionId")
	private UnitDefinition unitDefinition;
	
	private boolean radiation = false;
	
	private boolean poison = false;

	public FieldDefinition() {
		// TODO Auto-generated constructor stub
	}
	
	public FieldDefinition(String fieldDescription, Coord coord, Terrain terrain, UnitDefinition unitDefinition, boolean radiation, boolean poison) {
		super();
		this.fieldDescription = fieldDescription;
		this.coord = coord;
		this.terrain = terrain;
		this.unitDefinition = unitDefinition;
		this.radiation = radiation;
		this.poison = poison;
	}

	public String getFieldDescription() {
		return fieldDescription;
	}
	
	protected void setFieldDescription(String fieldDescription) {
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
	
	public Field newFieldInstance() throws ZgameException {
		Field newField = new Field();
		newField.setCoord(this.coord);
		newField.setTerrain(this.terrain);
		newField.setRadiation(this.radiation);
		newField.setPoison(this.poison);
		newField.setUnit(this.unitDefinition.newUnitInstance());
		newField.setFieldDefinition(this);
		return newField;
	}
	
}
