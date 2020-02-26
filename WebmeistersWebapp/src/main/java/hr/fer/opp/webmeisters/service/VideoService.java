package hr.fer.opp.webmeisters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.VideoRepository;
import hr.fer.opp.webmeisters.data.model.Video;
@Service
public class VideoService {
	
	@Autowired
	private VideoRepository videoRepository;

	public void saveNewVideo(Video vid) {
		videoRepository.save(vid);
		
	}

}
