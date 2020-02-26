package hr.fer.opp.webmeisters.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "reviews")

@OnDelete(action = OnDeleteAction.CASCADE)
public class Review {

	@EmbeddedId
	private VisitorEvent visitorEvent;

	@Column(name = "date_created_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreatedAt;


	@Column(name = "review_title")
	private String title;

	@Column(name = "review_text")
	private String text;

	public VisitorEvent getVisitorEvent() {
		return visitorEvent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDateCreatedAt() {
		return dateCreatedAt;
	}

	public void setDateCreatedAt(Date dateCreatedAt) {
		this.dateCreatedAt = dateCreatedAt;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVisitorEvent(VisitorEvent visitorEvent) {
		this.visitorEvent = visitorEvent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((visitorEvent == null) ? 0 : visitorEvent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (visitorEvent == null) {
			if (other.visitorEvent != null)
				return false;
		} else if (!visitorEvent.equals(other.visitorEvent))
			return false;
		return true;
	}
	
	
	

}
