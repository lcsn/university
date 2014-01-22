package de.lsn.playground.zgame.beans;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import de.lsn.playground.util.qualifier.Session;

public abstract class AbstractBackingBean {

	@Session
	@Inject
	protected HttpSession session;

	@Inject
	protected FacesContext facesContext;

	@Inject
	protected ExternalContext externalContext;
	
	protected void addMessage(Severity severity, String summary, String detail) {
		facesContext.addMessage("", new FacesMessage(severity, summary, detail));
	}
	
}
