package de.lsn.playground.zgame.beans;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public abstract class AbstractBackingBean {

	protected void addMessage(Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage(severity, summary, detail));
	}
	
}
