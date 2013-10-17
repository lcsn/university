package de.lsn.playground.entity.player;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lsn.playground.entity.ZgameEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="PlayerSlot")
public class PlayerSlot extends ZgameEntity {

	private int turn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "playerId")
	private Player player;
	
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
	
}
