package de.lsn.example.fscrud.model;

import de.lsn.example.fscrud.model.attributes.Name;
import de.lsn.example.fscrud.processing.anno.Path;

@Path("/customer")
public class Customer extends CrudEntity implements Client {

	private Name name;
	
	public Customer() {
		super();
	}
	
	public Customer(Name name) {
		super();
		this.name = name;
	}
	
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	
	
}
