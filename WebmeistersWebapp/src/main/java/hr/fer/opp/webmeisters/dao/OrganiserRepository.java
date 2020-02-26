package hr.fer.opp.webmeisters.dao;

import hr.fer.opp.webmeisters.data.model.Organiser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganiserRepository extends JpaRepository<Organiser, Integer> {

    public Organiser findByEmail(String mail);

    public Organiser findById(int id);
}
