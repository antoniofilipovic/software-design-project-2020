package hr.fer.opp.webmeisters.service;

import hr.fer.opp.webmeisters.data.form.LoginForm;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;

public interface ILoginService {
	public User registerUser(User user);
	
	
	public User findByEmail(String email);

}
