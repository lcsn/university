package de.lsn.playground.entity.player;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.lsn.playground.entity.ZgameEntity;

@SuppressWarnings("serial")
@NamedQueries({
	@NamedQuery(name=Player.FIND_BY_USERNAME_AND_PASSWORD, query="SELECT o FROM Player AS o WHERE o.username = :username AND o.password = :password")
})
@Entity
@Table(name="Player", uniqueConstraints={@UniqueConstraint(columnNames={"username"})})
public class Player extends ZgameEntity {

	public static final String FIND_BY_USERNAME_AND_PASSWORD = "Player.FIND_BY_USERNAME_AND_PASSWORD";

	@Size(max=12)
	private String username;
	
	@Size(max=32)
	@NotNull
	private String firstname;
	
	@Size(max=32)
	@NotNull
	private String lastname;
	
	@NotNull
	private String email;
	
	@Temporal(TemporalType.DATE)
	private Calendar birthday;
	
	@NotNull
	@Size(min=8, max=24)
	private String password;
	
	@Transient
	private String _password;
	
	/*
	 Score
	 Gender
	 Photo
	 */
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Calendar getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String get_password() {
		return _password;
	}
	
	public void set_password(String _password) {
		this._password = _password;
	}
	
}
