package hr.fer.opp.webmeisters.dao;

import hr.fer.opp.webmeisters.data.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Integer> {
}
