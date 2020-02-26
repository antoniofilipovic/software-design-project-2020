package hr.fer.opp.webmeisters.data.form;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import hr.fer.opp.webmeisters.annotations.PasswordMatches;
import hr.fer.opp.webmeisters.annotations.ValidEmail;
import hr.fer.opp.webmeisters.data.model.Visitor;

//@PasswordMatches()
public class VisitorForm {

	//@NotEmpty(message = "Name can't be empty")
	private String fullName;

	//@Column(length = 100, nullable = false, unique = true)
	//@ValidEmail
	private String email;

	
	//@NotEmpty()
	private String password;

	//@NotEmpty
	private String rePassword;

	//@NotEmpty(message = "Nick can't be empty")
	private String nick;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public void fillData(Visitor visitor) {
		this.email = visitor.getEmail();
		this.fullName = visitor.getFullName();
		this.nick=visitor.getNick();

	}

}
