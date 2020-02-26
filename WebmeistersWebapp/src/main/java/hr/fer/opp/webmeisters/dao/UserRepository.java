package hr.fer.opp.webmeisters.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.opp.webmeisters.data.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);

}
