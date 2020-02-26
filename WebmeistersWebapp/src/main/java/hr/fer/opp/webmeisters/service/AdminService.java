package hr.fer.opp.webmeisters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.AdminRepository;
import hr.fer.opp.webmeisters.data.model.Admin;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	public Admin getAdmin() {
		return adminRepository.findAll().get(0);
	}

	public void update(Admin admin) {
		adminRepository.save(admin);
		
	}

}
