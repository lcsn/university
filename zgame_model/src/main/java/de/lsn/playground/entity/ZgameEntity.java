package de.lsn.playground.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

@SuppressWarnings("serial")
@MappedSuperclass
@TableGenerator(name = "UniqueIdGenerator", table = "ID_GEN", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "globalkey", allocationSize = 10)
public abstract class ZgameEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "UniqueIdGenerator")
	protected Long id;

	public Long getId() {
		return id;
	}
	
	protected void setId(Long id) {
		this.id = id;
	}
	
}
