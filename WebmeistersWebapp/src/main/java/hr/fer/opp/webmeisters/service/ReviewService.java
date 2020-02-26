package hr.fer.opp.webmeisters.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.ReviewRepository;
import hr.fer.opp.webmeisters.data.form.ReviewForm;
import hr.fer.opp.webmeisters.data.model.Event;
import hr.fer.opp.webmeisters.data.model.Review;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.data.model.VisitorEvent;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;
	
	public void saveReview(ReviewForm reviewForm, Visitor visitor,Event event) {
		Review review=new Review();
		review.setText(reviewForm.getText());
		VisitorEvent visitorEvent=new VisitorEvent();
		visitorEvent.setEvent(event);
		visitorEvent.setVisitor(visitor);
		review.setVisitorEvent(visitorEvent);
		review.setTitle(reviewForm.getTitle());
		review.setDateCreatedAt(new Date());
		reviewRepository.save(review);
		
	}
	
	public void editReview(ReviewForm reviewForm, Visitor visitor,Event event) {
		Review review=new Review();
		review.setText(reviewForm.getText());
		VisitorEvent visitorEvent=new VisitorEvent();
		visitorEvent.setEvent(event);
		visitorEvent.setVisitor(visitor);
		review.setVisitorEvent(visitorEvent);
		review.setTitle(reviewForm.getTitle());
		
	}

	public List<Review> getAllReviewsByEventId(Event event) {
		return reviewRepository.findByVisitorEventEvent(event);
	}

	public Optional<Review> getReviewByVisitorForEvent(Visitor user, Event event) {
		VisitorEvent visitorEvent=new VisitorEvent();
		visitorEvent.setEvent(event);
		visitorEvent.setVisitor(user);
		
		return reviewRepository.findById(visitorEvent);
	
	}

	public void deleteReviewByVisitorForEvent(Visitor visitor, Event event) {
		VisitorEvent visitorEvent=new VisitorEvent();
		visitorEvent.setEvent(event);
		visitorEvent.setVisitor(visitor);
		reviewRepository.deleteById(visitorEvent);
		
	}

	public List<Review> getReviewForVisitor(Visitor visitor){
		List<Review> lista = reviewRepository.findByVisitorEventVisitor(visitor);
		return lista;
	}

	public List<Review> getReviewForEvent(Event event) {
		List<Review> lista = reviewRepository.findByVisitorEventEvent(event);
		return lista;
	}

}
