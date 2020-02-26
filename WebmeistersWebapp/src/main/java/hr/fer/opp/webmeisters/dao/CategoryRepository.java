package hr.fer.opp.webmeisters.dao;

import hr.fer.opp.webmeisters.data.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Integer> {

	public Category findByName(String name);
}
