package hr.fer.opp.webmeisters.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.InterestRepository;
import hr.fer.opp.webmeisters.dao.VisitorRepository;
import hr.fer.opp.webmeisters.data.model.Event;
import hr.fer.opp.webmeisters.data.model.Interest;
import hr.fer.opp.webmeisters.data.model.InterestType;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.data.model.VisitorEvent;
@Service
public class InterestService {
	@Autowired
	private InterestRepository interestRepository;
	
	public List<Interest> getAllInterests(){
		return interestRepository.findAll();
		
	}

	public void saveInterest(Event event, Visitor visitor, InterestType interestType) {
		Interest interest=new Interest();
		VisitorEvent visitorEvent=new VisitorEvent();
		visitorEvent.setEvent(event);
		visitorEvent.setVisitor(visitor);
		interest.setVisitorEventId(visitorEvent);
		interest.setInterest(interestType);
		System.out.println("sve oke");
		interestRepository.save(interest);
		
	}
	
	public Optional<Interest> getInterest(Event event, Visitor visitor) {
		Interest interest=null;
		VisitorEvent visitorEvent=new VisitorEvent();
		visitorEvent.setEvent(event);
		visitorEvent.setVisitor(visitor);
		
		return interestRepository.findById(visitorEvent);
		
		
	}

	public void deleteInterest(Event event, Visitor visitor) {
		VisitorEvent visitorEvent=new VisitorEvent();
		visitorEvent.setEvent(event);
		visitorEvent.setVisitor(visitor);
		
		interestRepository.deleteById(visitorEvent);
	}

	public List<Interest> getInterestsForVisitor(Visitor visitor){

		List<Interest> lista = interestRepository.findByVisitorEventIdVisitor(visitor);
		return lista;
	}

	public List<Interest> getInterestsForEvent(Event event){
		List<Interest> lista = interestRepository.findByVisitorEventIdEvent(event);
		return lista;
	}
}
