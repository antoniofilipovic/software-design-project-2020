package hr.fer.opp.webmeisters.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hr.fer.opp.webmeisters.data.form.ReviewForm;
import hr.fer.opp.webmeisters.data.model.Event;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.service.EventService;
import hr.fer.opp.webmeisters.service.InterestService;
import hr.fer.opp.webmeisters.service.VisitorService;
@Controller
public class InterestRegularController {
	
	@Autowired
	private InterestService interestService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private VisitorService visitorService;
	
	@RequestMapping(value = "/deleteInterest/{userId}/{eventId}", method = RequestMethod.GET)
	public String  deleteInterestMethod(@PathVariable String userId, @PathVariable String eventId,
			@ModelAttribute("reviewForm") ReviewForm reviewForm, BindingResult result, Model model,
			@SessionAttribute("loggedInUser") User user, HttpSession session, RedirectAttributes attributes) {
		
	
		Visitor visitor=visitorService.getVisitor(Integer.parseInt(userId));
		Event event=eventService.getEvent(Integer.parseInt(eventId));
		
		interestService.deleteInterest(event,visitor);
		

		return "redirect:/showEvent/"+eventId;

		
	}

}
