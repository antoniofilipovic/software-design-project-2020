package hr.fer.opp.webmeisters.dao;

import hr.fer.opp.webmeisters.data.model.Event;
import hr.fer.opp.webmeisters.data.model.Visitor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.opp.webmeisters.data.model.Interest;
import hr.fer.opp.webmeisters.data.model.VisitorEvent;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, VisitorEvent>  {

    List<Interest> findByVisitorEventIdVisitor(Visitor visitor);
    List<Interest> findByVisitorEventIdEvent(Event event);

}
