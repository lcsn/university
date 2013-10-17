package de.lsn.playground.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import de.lsn.playground.entity.player.Player;


@SuppressWarnings("serial")
@Entity
@Table(name="PlayerImage", uniqueConstraints={
		@UniqueConstraint(columnNames={"imageId", "playerId"})
	}
)
public class PlayerImage extends ZgameEntity {

	@Lob
	private byte[] image;

	private Long imageId = UUID.randomUUID().getMostSignificantBits();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "playerId")
	private Player player;

	public PlayerImage() {
		// TODO Auto-generated constructor stub
	}
	
	public PlayerImage(byte[] image, Player player) {
		this.image = image;
		this.player = player;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public Long getImageId() {
		return imageId;
	}
	
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	
	protected void setImage(byte[] image) {
		this.image = image;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
