package de.lsn.playground.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Test {

	@Id
	private Long id = UUID.randomUUID().getMostSignificantBits();
	
	public Long getId() {
		return id;
	}
	
}
