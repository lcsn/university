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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.entity.attribute.Name;
import de.lsn.playground.entity.player.Player;
import de.lsn.playground.entity.player.PlayerSlot;
import de.lsn.playground.framwork.exception.ZgameException;

@SuppressWarnings("serial")
@NamedQueries({
	@NamedQuery(name=Map.FIND_BY_ID, query="SELECT o FROM Map AS o WHERE o.id = :id"),
	@NamedQuery(name=Map.FIND_BY_PLAYER_ID, query="SELECT o FROM Map AS o INNER JOIN o.playerSlots AS p WHERE p.playerId = :playerId")
	
})
@Entity
@Table(name="Map")
public class Map extends ZgameEntity {

	public static final String FIND_BY_ID = "Map.FIND_BY_ID";
	public static final String FIND_BY_PLAYER_ID = "Map.FIND_BY_PLAYER_ID";

	@JsonIgnore
	private Long mapInstanceId = UUID.randomUUID().getMostSignificantBits();
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "mapId")
	private List<PlayerSlot> playerSlots = new ArrayList<PlayerSlot>();
	
	@Embedded
	@AttributeOverride(name="nameValue", column=@Column(name="mapName"))
	private Name mapName;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "mapId")
	private List<Field> fields;
	
	@JsonIgnore
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

	/**
	 * Adds the given player to the first vacant slot found for this map.
	 * 
	 * @param player of type {@link Player}
	 * @throws ZgameException 
	 */
	public void addPlayerToPlayerSlot(Player player) throws ZgameException {
		if(hasVacantPlayerSlot()) {
			for (PlayerSlot playerSlot : this.playerSlots) {
				if(playerSlot.isVacant()) {
					playerSlot.addPlayer(player);
				}
			}
		}
		else {
			throw new ZgameException("There are no vacant playerslots left for this map: " + this.toRepresentationString());
		}
	}
	
	public void removePlayerFromPlayerSlot(Player player) throws ZgameException {
		for (PlayerSlot playerSlot : this.playerSlots) {
			if(playerSlot.getPlayer().equals(player)) {
				playerSlot.removePlayer();
			}
		}
	}
	
	/**
	 * Determines if the inherited List playerSlot has further vacant slots.
	 * 
	 * @return result of type {@link Boolean}
	 */
	public boolean hasVacantPlayerSlot() {
		boolean result = false;
		for (PlayerSlot playerSlot : this.playerSlots) {
			result = playerSlot.isVacant();
			if(result) {
				break;
			}
		}
		return result;
	}

	public String toRepresentationString() {
		return this.mapName.toString() + " " + this.mapInstanceId;
	}

}
