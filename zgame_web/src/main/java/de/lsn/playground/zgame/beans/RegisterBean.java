package de.lsn.playground.zgame.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.ZgameConstants;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.logic.player.PlayerServiceDAOLocal;
import de.lsn.playground.zgame.security.HashService;

@Named
@RequestScoped
public class RegisterBean extends AbstractBackingBean {

	@EJB
	private PlayerServiceDAOLocal playerServiceDAO;
	
	private Player newPlayer;

	private boolean valid = false;

	@PostConstruct
	private void construct() {
		init();
	}
	
	public String register() {
		String page = facesContext.getViewRoot().getViewId();
		if(!newPlayer.checkPass()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Passwörter stimmen nicht überein", "");
			return "";
		}
		HttpSession session = (HttpSession) externalContext.getSession(false);
		newPlayer.setPassword(HashService.getDigest(/*newPlayer.getUsername(), */newPlayer.getPassword()));
		try {
			playerServiceDAO.createPlayer(this.newPlayer);
			session.setAttribute(ZgameConstants.PLAYER_SESSION_ATTRIBUTE, newPlayer);
			page = "main.xhtml";
		} catch (ZgameException e) {
			e.printStackTrace();
			addMessage(FacesMessage.SEVERITY_WARN, "Registrierung fehlgeschlagen", "Benutzer kann nicht registriert");
		}
		return page;
	}
	
	private void init() {
		this.newPlayer = new Player();
		Object reg_valid_value = session.getAttribute(ZgameConstants.REGISTRATION_VALIDATION_VALUE);
		this.valid = (null!=reg_valid_value?(boolean)reg_valid_value:false);
	}
	
	public void validateUsername() {
		Long c = playerServiceDAO.validateUsername(newPlayer.getUsername());
		if(null == c || c < 1) {
			this.valid = true;
		}
		else {
			this.valid = false;
			addMessage(FacesMessage.SEVERITY_INFO, "", "Benutzername bereits vergeben");
		}
		session.setAttribute(ZgameConstants.REGISTRATION_VALIDATION_VALUE, this.valid);
	}
	
	public Player getNewPlayer() {
		return newPlayer;
	}
	
	public void setNewPlayer(Player newPlayer) {
		this.newPlayer = newPlayer;
	}
	
	public boolean isValid() {
		return valid;
	}
	
}
