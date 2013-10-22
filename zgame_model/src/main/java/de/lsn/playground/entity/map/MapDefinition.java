package de.lsn.playground.entity.map;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.entity.attribute.Name;
import de.lsn.playground.entity.player.PlayerSlot;
import de.lsn.playground.framwork.exception.ZgameException;

@SuppressWarnings("serial")
@NamedQueries({
	@NamedQuery(name=MapDefinition.FIND_BY_ID, query="SELECT o FROM MapDefinition AS o WHERE o.id = :id")
})
@Entity
@Table(name="MapDefinition")
public class MapDefinition extends ZgameEntity {

public static final String FIND_BY_ID = "MapDefinition.FIND_BY_ID";

	//	@Size(min=2)
	private Integer numPlayers;

	private Integer mapHeight;
	
	private Integer mapWidth;
	
	@Embedded
	@AttributeOverride(name="nameValue", column=@Column(name="mapName"))
	private Name mapName;
	
	@OneToMany(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "mapDefinitionId")
	private List<FieldDefinition> fieldDefinitions;
	
	public MapDefinition() {
		// TODO Auto-generated constructor stub
	}
	
	public MapDefinition(int numPlayers, int mapHeight, int mapWidth, Name mapName, List<FieldDefinition> fieldDefinitions) {
		super();
		this.numPlayers = numPlayers;
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;
		this.mapName = mapName;
		this.fieldDefinitions = fieldDefinitions;
	}

	public Integer getNumPlayers() {
		return numPlayers;
	}
	
	protected void setNumPlayers(Integer numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public Integer getMapHeight() {
		return mapHeight;
	}
	
	protected void setMapHeight(Integer mapHeight) {
		this.mapHeight = mapHeight;
	}
	
	public Integer getMapWidth() {
		return mapWidth;
	}
	
	protected void setMapWidth(Integer mapWidth) {
		this.mapWidth = mapWidth;
	}
	
	public List<FieldDefinition> getFieldDefinitions() {
		return fieldDefinitions;
	}
	
	protected void setFieldDefinitions(List<FieldDefinition> fieldDefinitions) {
		this.fieldDefinitions = fieldDefinitions;
	}

	public Map newMapInstance() throws ZgameException {
		Map newMap = new Map();
		newMap.setMapName(this.mapName);
		List<Field> fields = new ArrayList<Field>();
		for (FieldDefinition fieldDefinition : this.fieldDefinitions) {
			fields.add(fieldDefinition.newFieldInstance());
		}
		newMap.setFields(fields);
		newMap.setMapDefinition(this);
		newMap.setPlayerSlots(new ArrayList<PlayerSlot>());
		for (int i = 0; i < this.numPlayers; i++) {
			newMap.getPlayerSlots().add(new PlayerSlot(i));
		}
		return newMap;
	}
	
}
