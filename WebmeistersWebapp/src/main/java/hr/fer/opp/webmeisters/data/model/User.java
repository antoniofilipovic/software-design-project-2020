package hr.fer.opp.webmeisters.data.model;

import javax.persistence.*;
import javax.validation.constraints.Email;

import hr.fer.opp.webmeisters.util.PasswordUtil;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "user_id")
	private int id;
	
	@Column(name = "full_name", nullable = false)
	private String fullName;
	
	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(name = "user_type")
	private String userType;
	
	public User() {
		
	}

	public User(String name, String email2, String password, String userType2) {
		this.fullName=name;
		this.email=email2;
		this.passwordHash=PasswordUtil.passwordEncrypt(password);
		this.userType=userType2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	

}
