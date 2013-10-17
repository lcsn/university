package de.lsn.playground.entity.map;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.entity.attribute.Name;

@SuppressWarnings("serial")
@Entity
@Table(name="MapDefinition")
public class MapDefinition extends ZgameEntity {

	@Size(min=2)
	private int numPlayers;

	@NotNull
	private int mapHeight;
	
	@NotNull
	private int mapWidth;
	
	@Embedded
	@AttributeOverride(name="nameValue", column=@Column(name="mapName"))
	private Name mapName;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "fieldDefinition")
	private List<Field> fields;
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	protected void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}
	
	protected void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
	
	public int getMapWidth() {
		return mapWidth;
	}
	
	protected void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	
	protected void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
}
