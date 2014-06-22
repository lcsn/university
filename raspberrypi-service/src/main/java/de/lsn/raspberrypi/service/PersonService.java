package de.lsn.raspberrypi.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.lsn.raspberrypi.model.Person;
import de.lsn.raspberrypi.util.MockDB;

@Path("/person")
public class PersonService {

	@GET
	@Path("/{id}/xml")
	@Produces(MediaType.APPLICATION_XML)
	public Person getPersonByIdAsXml(@PathParam("id") Long id) {
		Person p = null;
		for (Person _p : MockDB.persons) {
			if (_p.getId().equals(id)) {
				p = _p;
			}
		}
		return p;
	}
	
	@GET
	@Path("/{id}")
	public Person getPersonById(@PathParam("id") Long id) {
		Person p = null;
		for (Person _p : MockDB.persons) {
			if (_p.getId().equals(id)) {
				p = _p;
			}
		}
		return p;
	}
	
	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_XML)
	public List<Person> getPersons() {
		return MockDB.persons;
	}

}
