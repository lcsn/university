package de.lsn.playground.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.lsn.playground.framwork.FirebaseObject;
import de.lsn.playground.json.JsonHelper;
import de.lsn.playground.json.JsonObject;

@SuppressWarnings("serial")
@MappedSuperclass
@TableGenerator(name = "UniqueIdGenerator", table = "ID_GEN", pkColumnName = "ID_NAME", valueColumnName = "ID_VAL", pkColumnValue = "globalkey", allocationSize = 10)
public class ZgameEntity implements Serializable, JsonObject, FirebaseObject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "UniqueIdGenerator")
	protected Long id;

	@Temporal(TemporalType.DATE)
	private Calendar creationDate = Calendar.getInstance();
	
	@Temporal(TemporalType.DATE)
	private Calendar mergeDate;
	
	public Long getId() {
		return id;
	}
	
	protected void setId(Long id) {
		this.id = id;
	}
	
	public Calendar getCreationDate() {
		return creationDate;
	}
	
	protected void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Calendar getMergeDate() {
		return mergeDate;
	}
	
	protected void setMergeDate(Calendar mergeDate) {
		this.mergeDate = mergeDate;
	}
	
	public void merged() {
		this.setMergeDate(Calendar.getInstance());
	}
	
	public Long getFirebaseId() {
		return getId();
	}
	
	@JsonIgnore
	@Override
	public Class<? extends JsonObject> getTargetClass() {
		return this.getClass();
	}

	@Override
	public String toJson() {
		return JsonHelper.getInstance().getJsonFromObject(this);
	}
	
	@Override
	public HashMap<String,Object> toMap() {
		return JsonHelper.getInstance().getMapFromObject(this);
	}
	
}
