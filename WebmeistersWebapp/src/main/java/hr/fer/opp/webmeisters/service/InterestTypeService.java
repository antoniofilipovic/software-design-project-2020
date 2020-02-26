package hr.fer.opp.webmeisters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.InterestTypeRepository;
import hr.fer.opp.webmeisters.data.model.Interest;
import hr.fer.opp.webmeisters.data.model.InterestType;

@Service
public class InterestTypeService {
	@Autowired
	private InterestTypeRepository interestTypeRepository;
	
	public List<InterestType> getAllInterests(){
		return interestTypeRepository.findAll();
		
	}

	public InterestType getInterest(int interestTypeId) {
		return interestTypeRepository.getOne(interestTypeId);
	
	}
}
