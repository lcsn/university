package de.lsn.example.fscrud.view;

import javax.inject.Inject;

import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import de.lsn.example.fscrud.framework.MvcHelper;
import de.lsn.example.fscrud.service.EntityCrudService;

public class FsCrudMainController extends Controller {

	@Inject
	private EntityCrudService crudService;
	
	public FsCrudMainController() {
		this(null);
	}
	public FsCrudMainController(Controller parent) {
		MvcHelper.autowire(new FsCrudMainModel(), new FsCrudMainView());
		BeanProvider.getContextualReference(ContextControl.class);
		crudService.test();
	}
	
}
