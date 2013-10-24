package de.lsn.playground.zgame.beans;

import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import de.lsn.playground.entity.player.Player;
import de.lsn.playground.framwork.ZgameConstants;
import de.lsn.playground.framwork.exception.ZgameException;
import de.lsn.playground.logic.player.PlayerServiceDAOLocal;
import de.lsn.playground.util.qualifier.Request;
import de.lsn.playground.zgame.security.HashService;

@Named
@RequestScoped
public class LoginBean extends AbstractBackingBean {

	@EJB
	private PlayerServiceDAOLocal playerDAO;
	
	@Request
	@Inject
	protected HttpServletRequest request;
	
	private String username;
	
	private String password;
	
	public String enter() {
		String page = "login.xhtml";
		System.out.println("Login: "+username+":"+password);
		Player player = null;
		try {
			request.login(username, HashService.getDigest(username, password));
			player = playerDAO.findPlayerByUsernameAndPassword(username, HashService.getDigest(username, password));
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute(ZgameConstants.PLAYER_SESSION_ATTRIBUTE, player);
			page = "main.xhtml";
		} catch (ZgameException e) {
			e.printStackTrace();
			addMessage(FacesMessage.SEVERITY_WARN, "Login fehlgeschlagen", "Benutzername und/oder Passwort falsch");
		} catch (ServletException e) {
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
