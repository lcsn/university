package de.lsn.playground.zgame.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.ZgameConstants;
import de.lsn.playground.logic.player.PlayerServiceDAOLocal;
import de.lsn.playground.zgame.security.HashService;

@Named
@RequestScoped
public class RegisterBean extends AbstractBackingBean {

	@EJB
	private PlayerServiceDAOLocal playerServiceDAO;
	
	private Player newPlayer;

	@PostConstruct
	private void construct() {
		init();
	}
	
	public String register() {
		if(!newPlayer.checkPass()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Passwörter stimmen nicht überein", "");
			return "";
		}
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		newPlayer.setPassword(HashService.getDigest(newPlayer.getUsername(), newPlayer.getPassword()));
		playerServiceDAO.createPlayer(this.newPlayer);
		session.setAttribute(ZgameConstants.PLAYER_SESSION_ATTRIBUTE, newPlayer);
		return "main.xhtml";
	}
	
	private void init() {
		this.newPlayer = new Player();
	}
	
	public Player getNewPlayer() {
		return newPlayer;
	}
	
	public void setNewPlayer(Player newPlayer) {
		this.newPlayer = newPlayer;
	}
	
}
