package de.lsn.playground.zgame.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.ZgameConstants;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class PlayerBean implements Serializable {

	private Player player;
	
	@PostConstruct
	private void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.player = (Player) session.getAttribute(ZgameConstants.PLAYER_SESSION_ATTRIBUTE);
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
