package hr.fer.opp.webmeisters.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin extends User {
	//ovo ne brisi ni za zivu glavu, nekaj puca zbog tog bolje ne pitaj
	public Admin() {
		
	}
	
	public Admin(String name, String email, String password, String userType, String subscription) {
		super(name,email,password,userType);
		this.subscription=subscription;
	}

	@Column(name = "subscription")
	private String subscription;

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}
	
	
	

}
