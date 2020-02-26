package hr.fer.opp.webmeisters.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.opp.webmeisters.data.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
