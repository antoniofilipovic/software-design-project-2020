package hr.fer.opp.webmeisters.data.view;

import hr.fer.opp.webmeisters.data.model.Event;
import hr.fer.opp.webmeisters.data.model.Review;
import hr.fer.opp.webmeisters.data.model.Visitor;

public class ReviewView {
	
	private Event event;
	private Visitor visitor;
	private String dateCreatedAt;
	private String title;
	private String text;
	
	

	public void fillData(Review r) {
		this.text=r.getText();
		this.title=r.getTitle();
		this.dateCreatedAt=r.getDateCreatedAt().toString();
		this.event=r.getVisitorEvent().getEvent();
		this.visitor=r.getVisitorEvent().getVisitor();
		
		
	}



	public Event getEvent() {
		return event;
	}



	public void setEvent(Event event) {
		this.event = event;
	}



	public Visitor getVisitor() {
		return visitor;
	}



	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}



	public String getDateCreatedAt() {
		return dateCreatedAt;
	}



	public void setDateCreatedAt(String dateCreatedAt) {
		this.dateCreatedAt = dateCreatedAt;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}
	

}
