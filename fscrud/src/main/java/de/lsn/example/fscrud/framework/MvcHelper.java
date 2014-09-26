package de.lsn.example.fscrud.framework;

import java.util.logging.Logger;

import de.lsn.example.fscrud.view.MvcCrudModel;
import de.lsn.example.fscrud.view.MvcCrudView;

public class MvcHelper {

	private static Logger log = Logger.getLogger(MvcHelper.class.getSimpleName());
	
	public static void autowire(MvcCrudModel crudViewModel, MvcCrudView<?> crudView) {
		log.info("NOT YET IMPLEMENTED!");
	}

}
