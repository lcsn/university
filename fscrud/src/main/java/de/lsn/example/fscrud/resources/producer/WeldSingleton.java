package de.lsn.example.fscrud.resources.producer;

import org.jboss.weld.environment.se.WeldContainer;

public class WeldSingleton {

	private WeldContainer container;
	
	private static WeldSingleton instance;
	
	public static WeldSingleton getInstance() {
		if (null == instance) {
			instance = new WeldSingleton();
		}
		return instance;
	}
	
	public void setContainer(WeldContainer container) {
		this.container = container;
	}
	
	public WeldContainer getContainer() {
		return container;
	}
	
	public static WeldContainer container() {
		return getInstance().getContainer();
	}
	
}
