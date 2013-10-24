package de.lsn.playground.util;

import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.lsn.playground.util.qualifier.Request;
import de.lsn.playground.util.qualifier.Response;

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
	
}
