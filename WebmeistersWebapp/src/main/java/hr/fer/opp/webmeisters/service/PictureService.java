package hr.fer.opp.webmeisters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.PictureRepository;
import hr.fer.opp.webmeisters.data.model.Picture;

@Service
public class PictureService {
	
	@Autowired
	private PictureRepository pictureRepository;

	public void saveNewPicture(Picture slika) {
		pictureRepository.save(slika);
		
	}
	
	
	

}
