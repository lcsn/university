package de.lsn.playground.entity.map;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.entity.attribute.Name;
import de.lsn.playground.entity.player.PlayerSlot;

@SuppressWarnings("serial")
@Entity
@Table(name="Map")
public class Map extends ZgameEntity {

	private Long mapInstanceId = UUID.randomUUID().getMostSignificantBits();
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "mapId")
	private List<PlayerSlot> playerSlots = new ArrayList<PlayerSlot>();
	
	@Embedded
	@AttributeOverride(name="nameValue", column=@Column(name="mapName"))
	private Name mapName;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "mapId")
	private List<Field> fields;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mapDefinitionId")
	private MapDefinition mapDefinition;
	
	public Long getMapInstanceId() {
		return mapInstanceId;
	}
	
	protected void setMapInstanceId(Long mapInstanceId) {
		this.mapInstanceId = mapInstanceId;
	}
	
	public List<PlayerSlot> getPlayerSlots() {
		return playerSlots;
	}
	
	protected void setPlayerSlots(List<PlayerSlot> playerSlots) {
		this.playerSlots = playerSlots;
	}
	
	public Name getMapName() {
		return mapName;
	}
	
	protected void setMapName(Name mapName) {
		this.mapName = mapName;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	
	protected void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public MapDefinition getMapDefinition() {
		return mapDefinition;
	}
	
	protected void setMapDefinition(MapDefinition mapDefinition) {
		this.mapDefinition = mapDefinition;
	}
	
}
