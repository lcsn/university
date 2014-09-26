package de.lsn.example.fscrud.view;

import java.util.logging.Logger;

import de.lsn.example.fscrud.framework.MvcHelper;
import de.lsn.example.fscrud.service.CustomerCrudService;

public class FsCrudMainController extends Controller {

	private Logger log = Logger.getLogger(FsCrudMainController.class.getSimpleName());
	
	private CustomerCrudService customerService = new CustomerCrudService();
	
	public FsCrudMainController() {
		this(null);
	}
	public FsCrudMainController(Controller parent) {
		super();
		MvcHelper.autowire(new FsCrudMainModel(), new FsCrudMainView());
		customerService.test();
	}
	
}
