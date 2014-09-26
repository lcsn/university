package de.lsn.example.fscrud.view;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MvcCrudView<T> {

	private Component parent;
	private Component component;
	
	public MvcCrudView(JFrame frame, MvcCrudView<?> parent) {
		this.component = frame;
	}
	
	public MvcCrudView(JDialog dialog, MvcCrudView<?> parent) {
		this.component = dialog;
	}
	
	public MvcCrudView(JPanel dialog, MvcCrudView<?> parent) {
		this.component = dialog;
	}
	
	@SuppressWarnings("unchecked")
	public T getComponent() {
		return (T) component;
	}
	
	public Component getParent() {
		return parent;
	}
	
}
