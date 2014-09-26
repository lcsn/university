package de.lsn.example.fscrud;

import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import de.lsn.example.fscrud.view.FsCrudMainController;

public class FsCrudApplication {
	
	private Logger log = Logger.getLogger(FsCrudApplication.class.getSimpleName());
	
	public void start() {
		log.info("Starting FsCrudApp!");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new FsCrudMainController();
			}
		});
	}
	
	public void stop() {
		log.info("NOT YET IMPLEMENTED!");
	}
	
	public static void main(String[] args) {

		FsCrudApplication app = new FsCrudApplication();
		app.start();
		
		/*
		Weld weld = new Weld();
		WeldSingleton.getInstance().setContainer(weld.initialize());
		FsCrudApplication application = WeldSingleton.container().instance().select(FsCrudApplication.class).get();
	    application.start();
	    weld.shutdown();
	    */
		
	}

}
