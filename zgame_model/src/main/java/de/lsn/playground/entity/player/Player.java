package de.lsn.playground.entity.player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.lsn.playground.entity.Role;
import de.lsn.playground.entity.ZgameEntity;
import de.lsn.playground.framwork.Gender;
import de.lsn.playground.framwork.PlayerRole;

@SuppressWarnings("serial")
@NamedQueries({
	@NamedQuery(name=Player.FIND_BY_USERNAME_AND_PASSWORD, query="SELECT o FROM Player AS o WHERE o.username = :username AND o.password = :password")
})
@Entity
@Table(name="Player", uniqueConstraints={
		@UniqueConstraint(columnNames={"username"})
	}
)
public class Player extends ZgameEntity {

	public static final String FIND_BY_USERNAME_AND_PASSWORD = "Player.FIND_BY_USERNAME_AND_PASSWORD";

	@NotNull
	@Size(min=3)
	private String username;
	
	@NotNull
	@Size(min=3)
	private String firstname;
	
	@NotNull
	@Size(min=3)
	private String lastname;

	private Long score = 1000l;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Gender gender;
	
	@NotNull
	private String email;
	
	@Temporal(TemporalType.DATE)
	private Calendar birthday;
	
	@NotNull
	@Size(min=6)
	private String password;
	
	@Transient
	private String _password;
	
	private boolean locked;
	
	@OneToMany(mappedBy = "player", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Role> roles;

	@OneToOne(mappedBy = "player", fetch = FetchType.LAZY)
	private PlayerImage playerImage;
	
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
	
	public Long getScore() {
		return score;
	}
	
	protected void setScore(Long score) {
		this.score = score;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
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
	
	public boolean isLocked() {
		return locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public List<Role> getRoles() {
		return roles;
	}
	
	protected void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public boolean doesPassMatch() {
		boolean res = false;
		if(null != this.password && null != this._password) {
			if (this.password.equals(_password)) {
				res = true;
			}
		}
		return res;
	}

	@PrePersist
	private void prePersistAction() {
		if (null == this.roles) {
			this.roles = new ArrayList<Role>();
		}
		this.roles.add(new Role(this, PlayerRole.DEFAULT.val()));
	}
	
}
