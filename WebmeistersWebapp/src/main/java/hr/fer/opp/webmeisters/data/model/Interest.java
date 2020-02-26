package hr.fer.opp.webmeisters.data.model;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "interests")
@DynamicUpdate
public class Interest {
	
	
	@EmbeddedId
	private VisitorEvent visitorEventId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interestType_id", nullable = false)
	private InterestType interest;

	public VisitorEvent getVisitorEventId() {
		return visitorEventId;
	}

	public void setVisitorEventId(VisitorEvent visitorEventId) {
		this.visitorEventId = visitorEventId;
	}

	public InterestType getInterest() {
		return interest;
	}

	public void setInterest(InterestType interest) {
		this.interest = interest;
	}

	
	
	
	
	
	
	
	

}
