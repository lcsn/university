package de.lsn.playground.zgame.beans;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
		String page = facesContext.getViewRoot().getViewId();
		System.out.println("Login: "+username+":"+password);
		Player player = null;
		try {
			String passwordHash = HashService.getDigest(/*username, */password);
			request.login(username, password);
			player = playerDAO.findPlayerByUsernameAndPassword(username, passwordHash);
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
		session.setAttribute(ZgameConstants.PLAYER_SESSION_ATTRIBUTE, null);
		try {
			request.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "welcome.xhtml";
	}
	
	public String checkSession() {
		String appCtx = externalContext.getRequestContextPath();
		String page = facesContext.getViewRoot().getViewId();
		Player p = (Player) session.getAttribute(ZgameConstants.PLAYER_SESSION_ATTRIBUTE);
		if ((null != session && !(session.isNew())) && null != p) {
			try {
				page = appCtx+"/zgame/main.xhtml";
				externalContext.redirect(page);
				return page;
			} catch (IOException x) {
				x.printStackTrace();
			}
		}
		return "";
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
