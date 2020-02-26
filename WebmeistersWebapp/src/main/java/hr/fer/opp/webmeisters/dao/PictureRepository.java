package hr.fer.opp.webmeisters.dao;

import hr.fer.opp.webmeisters.data.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
}
