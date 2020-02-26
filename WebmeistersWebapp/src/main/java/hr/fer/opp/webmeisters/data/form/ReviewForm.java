package hr.fer.opp.webmeisters.data.form;

import hr.fer.opp.webmeisters.data.model.Review;

public class ReviewForm {
	
	private String title;
	private String text;
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
	public void fillForm(Review reviewForVisitorOnEvent) {
		this.title=reviewForVisitorOnEvent.getTitle();
		this.text=reviewForVisitorOnEvent.getText();
		
	}
	
	

}
