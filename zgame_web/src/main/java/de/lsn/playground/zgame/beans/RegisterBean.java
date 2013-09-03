package de.lsn.playground.zgame.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.ZgameConstants;
import de.lsn.playground.logic.player.PlayerDAOLocal;
import de.lsn.playground.zgame.security.HashService;

@Named
@RequestScoped
public class RegisterBean {

	@EJB
	private PlayerDAOLocal playerDAO;
	
	private Player newPlayer;

	@PostConstruct
	private void construct() {
		init();
	}
	
	public String register() {
		playerDAO.createPlayer(this.newPlayer);
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		newPlayer.setPassword(HashService.getRandomPasswordData(newPlayer.getUsername())[1]);
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
