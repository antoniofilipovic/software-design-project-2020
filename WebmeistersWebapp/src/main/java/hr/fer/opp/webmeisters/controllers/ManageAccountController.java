package hr.fer.opp.webmeisters.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.webmeisters.data.form.ReviewForm;
import hr.fer.opp.webmeisters.data.form.SubscriptionForm;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.service.AdminService;
import hr.fer.opp.webmeisters.service.OrganiserService;
import hr.fer.opp.webmeisters.service.UserService;
import hr.fer.opp.webmeisters.service.VisitorService;
import hr.fer.opp.webmeisters.util.PasswordUtil;

@Controller
public class ManageAccountController {
	@Autowired
	private VisitorService visitorService;
	
	@Autowired
	private OrganiserService organiserService;
	
	@Autowired
	private AdminService adminService;

	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/manageAccount/visitor/{id}", method = RequestMethod.GET)
	public String manageVisitorGetMethod(@PathVariable String id, Model model) {

		Visitor visitor = visitorService.findById(Integer.parseInt(id));
		model.addAttribute("visitor", visitor);
		
		model.addAttribute("name", visitor.getFullName());
        model.addAttribute("id", visitor.getId());
        model.addAttribute("email",visitor.getEmail());
        model.addAttribute("nick",visitor.getNick());
	
		return "/profileVisitor";
	}

	@RequestMapping(value = "/manageAccount/visitor/{id}", method = RequestMethod.POST)
	public String manageVisitorPostMethod(@PathVariable String id) {
		
		return "/profileVisitor";
	}

	@RequestMapping(value = "/manageAccount/organiser/{id}", method = RequestMethod.GET)
	public String manageOrganiserGetMethod(@PathVariable String id,Model model,@ModelAttribute("isWriting") String isWriting) {
		Organiser organiser = organiserService.findById(Integer.parseInt(id));
		model.addAttribute("organiser", organiser);
		
		model.addAttribute("name", organiser.getFullName());
        model.addAttribute("id", organiser.getId());
        model.addAttribute("email",organiser.getEmail());
        model.addAttribute("realAddress",organiser.getRealAddress());
        model.addAttribute("webAddress",organiser.getWebAddress());
        if(organiser.getAccountValidUntil().before(new Date())) {
        	model.addAttribute("isValid", "false");
        }
        else {
        	model.addAttribute("isValid", "true");
        }
        
		
		return "/profileOrganiser";
	}

	@RequestMapping(value = "/manageAccount/organiser/{id}", method = RequestMethod.POST)
	public String manageOrganiserPostMethod(@PathVariable String id) {
		
		return "/profileOrganiser";

	}

	
	
	
	@RequestMapping(value = "/manageAccount/admin/{id}", method = RequestMethod.GET)
	public String manageAdminGetMethod(@PathVariable String id, Model model,HttpSession session,@ModelAttribute("isWriting") String isWriting,@ModelAttribute("subscriptionForm") SubscriptionForm subscriptionFormRedirect) {
		

		User admin = userService.findById(Integer.parseInt(id));
		model.addAttribute("admin", admin);
		
		model.addAttribute("name", admin.getFullName());
        model.addAttribute("id", admin.getId());
        model.addAttribute("email",admin.getEmail());
	
        
        if (isWriting == null || isWriting.isEmpty()) {
			model.addAttribute("isWriting", "false");
			subscriptionFormRedirect=new SubscriptionForm();
			subscriptionFormRedirect.setSubscription(adminService.getAdmin().getSubscription());
		} else {
			model.addAttribute("isWriting", isWriting);
		}
        model.addAttribute("subscriptionForm", subscriptionFormRedirect);
		return "/profileAdmin";

	}

	@RequestMapping(value = "/manageAccount/admin/{id}", method = RequestMethod.POST)
	public String manageAdminPostMethod(@PathVariable String id) {
		
		return "/profileAdmin";
	}
	
	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable String id) {
		userService.deleteUser(Integer.parseInt(id));
		return "redirect:/admin/showUsers";
	}
	
	
	
		
}
