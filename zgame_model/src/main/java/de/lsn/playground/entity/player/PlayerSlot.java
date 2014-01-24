package de.lsn.playground.entity.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.entity.map.Map;

@SuppressWarnings("serial")
@Entity
@Table(name="PlayerSlot")
public class PlayerSlot extends ZgameEntity {

	private int turn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "playerId")
	private Player player;

	@Column(insertable=false, updatable=false)
	private Long playerId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mapId")
	private Map map;
	
	@Column(insertable=false, updatable=false)
	private Long mapId;
	
	public PlayerSlot() {
		// TODO Auto-generated constructor stub
	}
	
	public PlayerSlot(int turn) {
		this.turn = turn;
	}
	
	public int getTurn() {
		return turn;
	}
	
	protected void setTurn(int turn) {
		this.turn = turn;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	protected void setPlayer(Player player) {
		this.player = player;
	}
	
	public Long getPlayerId() {
		return playerId;
	}
	
	protected void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	
	public Map getMap() {
		return map;
	}
	
	protected void setMap(Map map) {
		this.map = map;
	}

	public Long getMapId() {
		return mapId;
	}
	
	protected void setMapId(Long mapId) {
		this.mapId = mapId;
	}
	
	@JsonIgnore
	public boolean isVacant() {
		return this.player==null;
	}

	public void addPlayer(Player player) {
		this.setPlayer(player);
	}
	
	public void removePlayer() {
		this.setPlayer(null);
	}
	
}
