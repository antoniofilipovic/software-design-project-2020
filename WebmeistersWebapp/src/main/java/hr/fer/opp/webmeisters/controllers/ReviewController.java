package hr.fer.opp.webmeisters.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import hr.fer.opp.webmeisters.data.form.EventForm;
import hr.fer.opp.webmeisters.data.form.ReviewForm;
import hr.fer.opp.webmeisters.data.model.Event;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.Picture;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Video;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.data.model.VisitorEvent;
import hr.fer.opp.webmeisters.service.EventService;
import hr.fer.opp.webmeisters.service.ReviewService;
import hr.fer.opp.webmeisters.service.VisitorService;
import hr.fer.opp.webmeisters.validator.ReviewFormValidator;

@Controller
public class ReviewController {
	@Autowired
	private ReviewFormValidator reviewFormValidator;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private VisitorService visitorService;

	@Autowired
	private EventService eventService;

	@RequestMapping(value = "/addReview/{userId}/{eventId}", method = RequestMethod.POST)
	public String  addReviewPostMethod(@PathVariable String userId, @PathVariable String eventId,
			@ModelAttribute("reviewForm") ReviewForm reviewForm, BindingResult result, Model model,
			@SessionAttribute("loggedInUser") User user, HttpSession session, RedirectAttributes attributes) {

		reviewFormValidator.validate(reviewForm, result);

		if (result.hasErrors()) {
			
			//session.setAttribute("reviewForm", reviewForm);
			attributes.addFlashAttribute("reviewForm", reviewForm);
			attributes.addFlashAttribute("isWriting", "true");
			attributes.addFlashAttribute("result", result);
			return "redirect:/showEvent/" + eventId;
		}

		else {
			Visitor visitor = visitorService.getVisitor(Integer.parseInt(userId));
			Event event = eventService.getEvent(Integer.parseInt(eventId));
			reviewService.saveReview(reviewForm, visitor, event);
			attributes.addFlashAttribute("isWriting", "false");
			return "redirect:/showEvent/" + eventId;

		}

		
	}
	
	
	
	
	
	@RequestMapping(value = "/deleteReview/{userId}/{eventId}", method = RequestMethod.GET)
	public String  deleteReviewMethod(@PathVariable String userId, @PathVariable String eventId,
			@ModelAttribute("reviewForm") ReviewForm reviewForm, BindingResult result, Model model,
			@SessionAttribute("loggedInUser") User user, HttpSession session, RedirectAttributes attributes) {
		
	
		Visitor visitor=visitorService.getVisitor(Integer.parseInt(userId));
		Event event=eventService.getEvent(Integer.parseInt(eventId));
		
		reviewService.deleteReviewByVisitorForEvent(visitor, event);
		

		return "redirect:/showEvent/" + eventId;

		
	}
}
