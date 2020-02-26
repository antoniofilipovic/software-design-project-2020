package hr.fer.opp.webmeisters.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import hr.fer.opp.webmeisters.data.model.Event;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.data.view.EventView;
import hr.fer.opp.webmeisters.service.EventService;
import hr.fer.opp.webmeisters.service.OrganiserService;
import hr.fer.opp.webmeisters.util.ValidationChecker;

@Controller
public class HomeController {

	@Autowired
	private EventService eventService;

	@Autowired
	private OrganiserService organiserService;
	
	
	@RequestMapping(value = "/testfacebook")
	public String testFacebook(Model model, HttpSession session) {

		

		return "testfacebook";
	}
	

	@RequestMapping(value = "/home")
	public String getHomeAndLoginMethod(Model model, HttpSession session) {

		List<Event> events = eventService.getAllEvents();

		List<EventView> views = new ArrayList<>();

		for (Event e : events) {
			EventView view = new EventView();
			view.fillData(e);

			views.add(view);
		}

		User user = (User) session.getAttribute("loggedInUser");

		if (user != null) {

			model.addAttribute("isOrganiser", (user instanceof Organiser) ? "true" : "false");
			model.addAttribute("isVisitor", (user instanceof Visitor) ? "true" : "false");
		}
		if(user instanceof Organiser) {
			Organiser org=(Organiser) user;
			if(org.getAccountValidUntil().before(new Date())) {
				System.out.println("Nije validan");
	        	model.addAttribute("isValid", "false");
	        }
	        else {
	        	model.addAttribute("isValid", "true");
	        	System.out.println("validan");
	        }
			//ValidationChecker.validateOrganiser(org, model);
		}

		

		model.addAttribute("events", views);

		return "home";
	}

	@RequestMapping(value = "/home/showOrganisers")
	public String showOrganisers(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loggedInUser");

		if (user != null && user instanceof Visitor) {
			List<Organiser> organisers = organiserService.getAll();
			model.addAttribute("organisers", organisers);
		}

		return "showOrganisers";
	}

}
