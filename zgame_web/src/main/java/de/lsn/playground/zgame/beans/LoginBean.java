package de.lsn.playground.zgame.beans;

import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.ZgameConstants;
import de.lsn.playground.framwork.ZgameException;
import de.lsn.playground.logic.player.PlayerDAOLocal;
import de.lsn.playground.zgame.security.HashService;

@Named
@RequestScoped
public class LoginBean {

	@EJB
	private PlayerDAOLocal playerDAO;
	
	private String username;
	
	private String password;
	
	public String enter() {
		String page = "login.xhtml";
		System.out.println("Login: "+username+":"+password);
		Player player = null;
		try {
			player = playerDAO.findPlayerByUsernameAndPassword(username, HashService.getDigest(username, password));
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute(ZgameConstants.PLAYER_SESSION_ATTRIBUTE, player);
			page = "main.xhtml";
		} catch (ZgameException e) {
			e.printStackTrace();
			addMessage(FacesMessage.SEVERITY_WARN, "Login fehlgeschlagen", "Benutzername und/oder Passwort falsch");
		}
		return page;
	}

	public String exit() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		return "welcome.xhtml";
	}
	
	private void addMessage(Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage(severity, summary, detail));
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
