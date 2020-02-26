package hr.fer.opp.webmeisters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.OrganiserRepository;
import hr.fer.opp.webmeisters.dao.UserRepository;
import hr.fer.opp.webmeisters.dao.VisitorRepository;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrganiserService organiserService;
	
	@Autowired
	private VisitorService visitorService;

	public User findById(int parseInt) {
		return userRepository.getOne(parseInt);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
		
	}

	public void deleteUser(int parseInt) {
		User user=userRepository.getOne(parseInt);
		if(user.getUserType().equals("visitor")) {
			visitorService.deleteVisitor(parseInt);
			
		}else {
			organiserService.deleteOrganiser(parseInt);
		}
		//userRepository.deleteById(parseInt);
		
	}
	
	
	
}
