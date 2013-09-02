package de.lsn.playground.zgame.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class LoginBean {

	@Inject
	protected HttpServletRequest request;
	
	private String username;
	
	private String password;
	
	public String enter() {
		System.out.println("Login: "+username+":"+password);
		
		HttpSession session = request.getSession();
		
		session.setAttribute("player", username);
		
		return "main.xhtml";
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
