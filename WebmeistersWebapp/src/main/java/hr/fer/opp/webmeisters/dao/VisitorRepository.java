package hr.fer.opp.webmeisters.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.opp.webmeisters.data.model.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Integer>{
	
	public Visitor findByEmail(String email);

	public Visitor findByNick(String nick);

	public Visitor findById(int id);
	
}
