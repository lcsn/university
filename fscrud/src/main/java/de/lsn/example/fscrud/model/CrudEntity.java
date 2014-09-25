package de.lsn.example.fscrud.model;

import java.time.LocalDate;
import java.util.UUID;

import de.lsn.example.fscrud.processing.anno.AppRoot;
import de.lsn.example.fscrud.processing.anno.Path;

@AppRoot
@Path("/entities")
public class CrudEntity {
	
	private Long id;

	private LocalDate creationDate = LocalDate.now();
	
	public CrudEntity() {
		this.id = UUID.randomUUID().getLeastSignificantBits();
	}
	
	public Long getId() {
		return id;
	}
	
	protected void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}
	
	protected void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	
}
