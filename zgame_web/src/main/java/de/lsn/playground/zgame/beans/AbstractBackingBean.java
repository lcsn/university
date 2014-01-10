package de.lsn.playground.zgame.beans;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import de.lsn.playground.util.qualifier.Session;

public abstract class AbstractBackingBean {

	@Session
	@Inject
	protected HttpSession session;
	
	protected void addMessage(Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage(severity, summary, detail));
	}
	
}
