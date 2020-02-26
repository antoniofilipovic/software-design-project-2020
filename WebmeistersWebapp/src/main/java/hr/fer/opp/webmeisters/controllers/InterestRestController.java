package hr.fer.opp.webmeisters.controllers;

import javax.servlet.http.HttpSession;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import hr.fer.opp.webmeisters.data.form.ReviewForm;
import hr.fer.opp.webmeisters.data.model.Event;
import hr.fer.opp.webmeisters.data.model.InterestType;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.service.EventService;
import hr.fer.opp.webmeisters.service.InterestService;
import hr.fer.opp.webmeisters.service.InterestTypeService;
import hr.fer.opp.webmeisters.service.VisitorService;

@RestController
public class InterestRestController {
	
	@Autowired
	private InterestTypeService interestTypeService;
	
	@Autowired
	private InterestService interestService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private VisitorService visitorService;
	
	
	
	
	@PostMapping("saveInterest/{eventId}/{userId}/{interestTypeId}")
	public String saveInterestForVisitorOnEvent(@RequestParam(defaultValue = "test") String dummy,@PathVariable String eventId, @PathVariable String userId,
			@PathVariable String interestTypeId) {
		System.out.println(dummy);
		System.out.println("u save interest sam");
		Visitor visitor=visitorService.getVisitor(Integer.parseInt(userId));
		if(visitor==null) {
			System.out.println("visitor je null");
		}
		Event event=eventService.getEvent(Integer.parseInt(eventId));
		if(event==null) {
			System.out.println("event je null");
		}
		InterestType interestType=interestTypeService.getInterest(Integer.parseInt(interestTypeId));
		if(interestType==null) {
			System.out.println("interestType je null");
		}
		
		System.out.println("priprema");
		interestService.saveInterest(event,visitor,interestType);
		System.out.println("spremio sam");
		
		
		return interestType.getName();
			
		
		

	}
	

	

}
