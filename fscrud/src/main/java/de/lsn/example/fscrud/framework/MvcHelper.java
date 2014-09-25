package de.lsn.example.fscrud.framework;

import java.util.logging.Logger;

import javax.inject.Inject;

import de.lsn.example.fscrud.view.CrudView;
import de.lsn.example.fscrud.view.CrudViewModel;

public class MvcHelper {

	@Inject
	private Logger log;
	
	public static void autowire(CrudViewModel crudViewModel, CrudView crudView) {
//		log.info("NOT YET IMPLEMENTED!");
	}

}
