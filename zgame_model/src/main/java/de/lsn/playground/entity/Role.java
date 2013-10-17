package de.lsn.playground.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import de.lsn.playground.entity.attribute.Name;
import de.lsn.playground.entity.player.Player;


@SuppressWarnings("serial")
@Entity
@Table(name="Role", uniqueConstraints={
		@UniqueConstraint(columnNames={"roleName", "playerId"})
	}
)
public class Role extends ZgameEntity {

	@Embedded
	@AttributeOverride(name="nameValue", column=@Column(name="rolename"))
	private Name roleName; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "playerId")
	private Player player;

	public Role() {
		// TODO Auto-generated constructor stub
	}
	
	public Role(Player player, String roleName) {
		this.roleName = new Name(roleName);
		this.player = player;
	}
	
	public Name getRoleName() {
		return roleName;
	}

	public void setRoleName(Name roleName) {
		this.roleName = roleName;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
