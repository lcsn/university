package de.lsn.example.fscrud;

import javax.swing.SwingUtilities;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;

import de.lsn.example.fscrud.view.FsCrudMainController;

public class FsCrudApplication {

	private CdiContainer cdiContainer;
	
	public void start() {
		cdiContainer = CdiContainerLoader.getCdiContainer();
		cdiContainer.boot();
		cdiContainer.getContextControl().startContexts();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new FsCrudMainController();
			}
		});
		stop();
	}
	
	public void stop() {
        cdiContainer.getContextControl().stopContexts();
		cdiContainer.shutdown();
		clean();
	}
	
	private void clean() {
		System.out.println("NOT YET IMPLEMENTED!");
	}

	public static void main(String[] args) {

		FsCrudApplication app = new FsCrudApplication();
		app.start();
		
	}

}
