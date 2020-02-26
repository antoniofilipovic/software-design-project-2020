package hr.fer.opp.webmeisters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.CategoryRepository;
import hr.fer.opp.webmeisters.data.model.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}

	public Category findByName(String category) {
		return categoryRepository.findByName(category);
	}

}
