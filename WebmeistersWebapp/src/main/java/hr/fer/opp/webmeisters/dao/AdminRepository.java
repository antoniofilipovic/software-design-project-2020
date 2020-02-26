package hr.fer.opp.webmeisters.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.opp.webmeisters.data.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
