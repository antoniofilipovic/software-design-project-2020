package hr.fer.opp.webmeisters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.AreaRepository;
import hr.fer.opp.webmeisters.data.model.Area;

@Service
public class AreaService {
	
	@Autowired
	private AreaRepository areaRepository;

	public List<Area> getAllAreas() {
		return areaRepository.findAll();
	}

	public Area findByName(String area) {
		return areaRepository.findByName(area);
	}
	
	

}
