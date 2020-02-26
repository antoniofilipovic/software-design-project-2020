package hr.fer.opp.webmeisters.dao;

import java.util.List;

import hr.fer.opp.webmeisters.data.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.opp.webmeisters.data.model.Event;
import hr.fer.opp.webmeisters.data.model.Review;
import hr.fer.opp.webmeisters.data.model.VisitorEvent;

public interface ReviewRepository  extends JpaRepository<Review, VisitorEvent>{
	public List<Review> findByVisitorEventEvent(Event event);

	public List<Review> findByVisitorEventVisitor(Visitor visitor);

}
