package hr.fer.opp.webmeisters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import hr.fer.opp.webmeisters.dao.AdminRepository;
import hr.fer.opp.webmeisters.dao.AreaRepository;
import hr.fer.opp.webmeisters.dao.CategoryRepository;
import hr.fer.opp.webmeisters.dao.InterestTypeRepository;
import hr.fer.opp.webmeisters.dao.UserRepository;
import hr.fer.opp.webmeisters.data.model.Admin;
import hr.fer.opp.webmeisters.data.model.Area;
import hr.fer.opp.webmeisters.data.model.Category;
import hr.fer.opp.webmeisters.data.model.Interest;
import hr.fer.opp.webmeisters.data.model.InterestType;
import hr.fer.opp.webmeisters.data.model.User;

@Component
class Initializer implements CommandLineRunner {
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private InterestTypeRepository interestTypeRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Override
	public void run(String... args) throws Exception {
		if (adminRepository.findAll().isEmpty()) {
			adminRepository.save(new Admin("Admin", "admin@admin", "admin", "admin", "200"));
		}
		if (categoryRepository.findAll().isEmpty()) {

			categoryRepository.save(new Category("Diskoteka"));
			categoryRepository.save(new Category("Kino"));
			categoryRepository.save(new Category("Kazalište"));
			categoryRepository.save(new Category("Sport"));
			categoryRepository.save(new Category("Koncert"));
		}

		if (interestTypeRepository.findAll().isEmpty()) {

			interestTypeRepository.save(new InterestType("Sigurno dolazim"));
			interestTypeRepository.save(new InterestType("Možda dolazim"));
			interestTypeRepository.save(new InterestType("Ne dolazim"));
		}
		if (areaRepository.findAll().isEmpty()) {

			areaRepository.save(new Area("Zagreb"));
			areaRepository.save(new Area("Zadar"));
			areaRepository.save(new Area("Rijeka"));
			areaRepository.save(new Area("Split"));
			areaRepository.save(new Area("Osijek"));
		}


	}
}