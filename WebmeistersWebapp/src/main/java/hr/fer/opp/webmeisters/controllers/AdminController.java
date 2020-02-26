package hr.fer.opp.webmeisters.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hr.fer.opp.webmeisters.data.form.ReviewForm;
import hr.fer.opp.webmeisters.data.form.SubscriptionForm;
import hr.fer.opp.webmeisters.data.model.Admin;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.service.AdminService;
import hr.fer.opp.webmeisters.service.UserService;
import hr.fer.opp.webmeisters.validator.EventFormValidator;
import hr.fer.opp.webmeisters.validator.SubscriptionFormValidator;

@Controller
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private SubscriptionFormValidator subscriptionFormValidator;
	
	@RequestMapping(value = "/admin/setSubscription/{id}", method = RequestMethod.POST)
	public String adminSetSubscriptionGet(@PathVariable String id,Model model, HttpSession session,
			@ModelAttribute("subscriptionForm") SubscriptionForm subscriptionForm,BindingResult result,
			RedirectAttributes attributes) {
		
		
		subscriptionFormValidator.validate(subscriptionForm, result);
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("subscriptionForm", subscriptionForm);
			attributes.addFlashAttribute("isWriting", "true");
			
		}else {
			Admin admin=adminService.getAdmin();
			admin.setSubscription(subscriptionForm.getSubscription());
			adminService.update(admin);
			attributes.addFlashAttribute("isWriting", "");
		}

		
		return "redirect:/manageAccount/admin/"+id;
	}
	
	@RequestMapping(value = "/admin/showUsers", method = RequestMethod.GET)
	public String adminManageUsersGet( Model model) {
		List<User> users=userService.getAllUsers();
		Admin admin=adminService.getAdmin();
		users.remove(admin);
		model.addAttribute("users", users);
		return "showUsers";
	}

}
