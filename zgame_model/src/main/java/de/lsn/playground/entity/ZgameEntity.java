package de.lsn.playground.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.lsn.playground.json.JsonObject;

@SuppressWarnings("serial")
@MappedSuperclass
@TableGenerator(name = "UniqueIdGenerator", table = "ID_GEN", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "globalkey", allocationSize = 10)
public class ZgameEntity implements Serializable, JsonObject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "UniqueIdGenerator")
	protected Long id;

	public Long getId() {
		return id;
	}
	
	protected void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	@Override
	public Class<? extends JsonObject> getTargetClass() {
		return this.getClass();
	}

	@Override
	public String toJson() {
		return de.lsn.playground.json.JsonHelper.getInstance().getJsonFromObject(this);
	}
	
}
