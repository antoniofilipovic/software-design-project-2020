package hr.fer.opp.webmeisters.data.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "visitors")
public class Visitor extends User{
	
	
	private String nick;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")})
	private Set<Review> reviews;
	
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")})
	private Set<Interest> interests;
	
	@OneToOne
	@JoinColumn(name = "notification_id")
	private Notification notification;
	
	
	
	
//	@OneToOne
//	@JoinColumn(name = "notification_id", nullable = false)
//	private Notification visitor;
	

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<Interest> getInterests() {
		return interests;
	}

	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}

	
	
	

	
	
	
	
	
	

}
