package de.lsn.playground.zgame.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class PlayerBean implements Serializable {

	private String player;
	
	@PostConstruct
	private void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.player = (String) session.getAttribute("player");
	}
	
	public String getPlayer() {
		return player;
	}
	
}
