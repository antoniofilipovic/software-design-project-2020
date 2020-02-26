package hr.fer.opp.webmeisters.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "paypals")
public class PayPal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="paypal_id")
	private int id;
	
	@Column(name="paypal_email")
	private String payPalEmail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPayPalEmail() {
		return payPalEmail;
	}

	public void setPayPalEmail(String payPalEmail) {
		this.payPalEmail = payPalEmail;
	}


	
	

}
