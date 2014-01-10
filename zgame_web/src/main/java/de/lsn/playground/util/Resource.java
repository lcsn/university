package de.lsn.playground.util;

import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.lsn.playground.util.qualifier.Request;
import de.lsn.playground.util.qualifier.Response;
import de.lsn.playground.util.qualifier.Session;

public class Resource {

	@Produces
	public FacesContext produceFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	@Produces
	public ExternalContext produceExternalContext() {
		return produceFacesContext().getExternalContext();
	}
	
	@Request
	@Produces
	public HttpServletRequest produceServletRequest() {
		return (HttpServletRequest) produceExternalContext().getRequest();
	}

	@Response
	@Produces
	public HttpServletResponse produceServletResponse() {
		return (HttpServletResponse) produceExternalContext().getResponse();
	}
	
	@Session
	@Produces
	public HttpSession produceHttpSession() {
		return (HttpSession) produceExternalContext().getSession(false);
	}
	
}
