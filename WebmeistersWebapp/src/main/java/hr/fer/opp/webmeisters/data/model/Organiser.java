package hr.fer.opp.webmeisters.data.model;


import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



@Entity
@Table(name = "organisers")
public class Organiser extends User {
	
	private String realAddress;

	
	private String webAddress;
	
	@Column(name = "account_valid_until",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date accountValidUntil;
	
	@OneToMany(mappedBy = "organiser",fetch = FetchType.LAZY)
	private Set<Event> events;
	
	@OneToOne
    @JoinColumn(name = "credit_card_id", nullable = true)
	private CreditCard creditCard;
	
	
	@OneToOne
    @JoinColumn(name = "paypal_id", nullable = true)
	private PayPal payPal;
	
	
	

	public String getRealAddress() {
		return realAddress;
	}

	public void setRealAddress(String realAddress) {
		this.realAddress = realAddress;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

//	public EditInfoOrganiserForm createEditInfoOrganiserForm(){
//		EditInfoOrganiserForm form = new EditInfoOrganiserForm();
//		form.setEmail(this.getEmail());
//		form.setName(this.getFullName());
//		form.setPassword(this.getPasswordHash());
//		form.setRepassword(this.getPasswordHash());
//		form.setRealAddress(this.realAddress);
//		form.setWebAddress(this.webAddress);
//
//		return form;
//	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public PayPal getPayPal() {
		return payPal;
	}

	public void setPayPal(PayPal payPal) {
		this.payPal = payPal;
	}

	public Date getAccountValidUntil() {
		return accountValidUntil;
	}

	public void setAccountValidUntil(Date accountValidUntil) {
		this.accountValidUntil = accountValidUntil;
	}
	
	

	
	

	
	
	

}
