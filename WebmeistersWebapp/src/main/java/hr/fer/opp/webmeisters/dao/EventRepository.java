package hr.fer.opp.webmeisters.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.opp.webmeisters.data.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
