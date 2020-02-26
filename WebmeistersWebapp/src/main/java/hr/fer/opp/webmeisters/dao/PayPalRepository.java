package hr.fer.opp.webmeisters.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.opp.webmeisters.data.model.CreditCard;
import hr.fer.opp.webmeisters.data.model.PayPal;

public interface PayPalRepository extends JpaRepository<PayPal, Integer> {

}
