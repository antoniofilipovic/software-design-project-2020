package hr.fer.opp.webmeisters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.fer.opp.webmeisters.dao.UserRepository;
import hr.fer.opp.webmeisters.data.form.LoginForm;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.exception.EmailExistsException;
import hr.fer.opp.webmeisters.util.PasswordUtil;

@Service
public class LoginService implements ILoginService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrganiserService orgService;
	
	@Autowired
	private VisitorService visitorService;


	@Transactional
	public User userAccountExists(LoginForm loginForm) throws EmailExistsException {
		User user=userRepository.findByEmail(loginForm.getEmail());
		if(user==null) {
			return null;
		}
		
		if( PasswordUtil.passwordEncrypt(loginForm.getPassword()).equals(user.getPasswordHash())==true) {
			return user;
		}return null;
		
	
		
	}
	
	


	@Override
	public User registerUser(User user) {
		User newUser;
		if(orgService.isOrganiser(user.getId())){
			newUser = orgService.findOrganiser(user.getId());
		} else if(visitorService.isVisitor(user.getId())) {
			newUser = visitorService.findById(user.getId());
		} else {
			newUser = userRepository.getOne(user.getId());
		}
		return newUser;
	}

	




	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}



	

	

	

}