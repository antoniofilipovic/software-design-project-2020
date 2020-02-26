package hr.fer.opp.webmeisters.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hr.fer.opp.webmeisters.data.form.EventForm;
import hr.fer.opp.webmeisters.data.form.NotificationForm;
import hr.fer.opp.webmeisters.data.model.Category;
import hr.fer.opp.webmeisters.data.model.Notification;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.service.AreaService;
import hr.fer.opp.webmeisters.service.CategoryService;
import hr.fer.opp.webmeisters.service.NotificationService;
import hr.fer.opp.webmeisters.service.OrganiserService;
import hr.fer.opp.webmeisters.service.VisitorService;
import hr.fer.opp.webmeisters.validator.NotificationFormValidator;

@Controller
public class NotificationController {

	@Autowired
	private VisitorService visitorService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private NotificationFormValidator notificationFormValidator;
	
	@Autowired
	private NotificationService notificationService;
	

	@RequestMapping(value = "/notification/{id}", method = RequestMethod.GET)
	public String showNotificationStatus(@PathVariable String id, Model model) {

		Visitor visitor = visitorService.findById(Integer.parseInt(id));
		model.addAttribute("visitor", visitor);

		if (visitor.getNotification() == null) {
			model.addAttribute("hasNotification", "false");
		} else {
			model.addAttribute("hasNotification", "true");
			Notification n=visitor.getNotification();
			List<String> categories=new ArrayList<>();
			List<String> areas=new ArrayList<>();
			n.getCategories().forEach(e->categories.add(e.getName()));
			n.getAreas().forEach(e->areas.add(e.getName()));
			model.addAttribute("notification", n);
			model.addAttribute("categories",categories);
			
			model.addAttribute("areas", areas);
		}

		return "notification";
	}

	@RequestMapping(value = "/notification/add/{id}", method = RequestMethod.GET)
	public String addNotificationStatus(@PathVariable String id, Model model) {

		Visitor visitor = visitorService.findById(Integer.parseInt(id));
		model.addAttribute("visitor", visitor);

		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("areas", areaService.getAllAreas());
		model.addAttribute("notificationForm", new NotificationForm());
		System.out.println("GEEET");

		return "notificationAdd";
	}

	@RequestMapping(value = "/notification/add/{id}", method = RequestMethod.POST)
	public String addNotificationPost(@PathVariable String id, Model model,
			@ModelAttribute("notificationForm") NotificationForm notificationForm, BindingResult result) {
		System.out.println("UŠLI TUUUUUU");
		
		
		Visitor visitor = visitorService.findById(Integer.parseInt(id));
		model.addAttribute("visitor", visitor);
		System.out.println(notificationForm.getAreas().toString());
		System.out.println(notificationForm.getCategories().toString());
		notificationFormValidator.validate(notificationForm, result);
		if(result.hasErrors()) {
			model.addAttribute("categories", categoryService.getAllCategories());
			model.addAttribute("areas", areaService.getAllAreas());
			model.addAttribute("notificationForm", notificationForm);
			return "notificationAdd";
		}
		
		
		notificationService.save(notificationForm,visitor);
		

		return "redirect:/notification/"+id;
	}
	
	@RequestMapping(value = "/notification/delete/{userId}/{notificationId}", method = RequestMethod.GET)
	public String deleteNotification(@PathVariable String userId, @PathVariable String notificationId,Model model) {

		Visitor visitor = visitorService.findById(Integer.parseInt(userId));
		
		Notification notification=notificationService.findById(Integer.parseInt(notificationId));
		notificationService.delete(notification, visitor);
		

		return "redirect:/notification/"+userId;
	}

}
