package hr.fer.opp.webmeisters.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.opp.webmeisters.data.model.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

}
