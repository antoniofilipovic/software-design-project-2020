package hr.fer.opp.webmeisters.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.opp.webmeisters.data.model.Area;

public interface AreaRepository extends JpaRepository<Area, Integer> {

	public Area findByName(String name);

}
