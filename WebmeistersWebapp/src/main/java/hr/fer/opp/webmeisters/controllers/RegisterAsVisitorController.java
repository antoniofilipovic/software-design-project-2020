package hr.fer.opp.webmeisters.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import hr.fer.opp.webmeisters.service.GetUserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.webmeisters.data.form.VisitorForm;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.exception.EmailExistsException;
import hr.fer.opp.webmeisters.service.VisitorService;
import hr.fer.opp.webmeisters.validator.VisitorFormValidator;
@Controller
public class RegisterAsVisitorController {
	
	
	@Autowired
	private VisitorService visitorService;
	
	@Autowired
    private VisitorFormValidator visitorFormValidator;
	
	
	
	
	@RequestMapping(value = "/registerAsVisitor", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
		VisitorForm visitorForm = new VisitorForm();
	    model.addAttribute("visitorForm", visitorForm);
	    return "registerVisitor";
	}
	
	@RequestMapping(value = "/registerAsVisitor", method = RequestMethod.POST)
	public ModelAndView registerUserAccount(@ModelAttribute("visitorForm") VisitorForm visitorForm, BindingResult result, Model model, HttpServletRequest req) {
		
		visitorFormValidator.validate(visitorForm, result);

        

		//Visitor registered = new Visitor();
		
		
		if (!result.hasErrors()) {
			System.out.println(visitorForm.getFullName());
			Visitor visitor = createVisitorAccount(visitorForm, result);
			req.getSession().setAttribute("loggedInUser", visitor);
			req.getSession().setAttribute("userType", GetUserTypeService.getType(visitor));
		}
	
		if (result.hasErrors()) {
			
			return new ModelAndView("registerVisitor", "visitor", visitorForm);
		} else {
			return new ModelAndView("thanksForRegistration", "visitor", visitorForm);
		}
		
		/*
		 * if(result.hasErrors()){
			return "register";
		}
		userService.createUser(user);
		return "redirect:/listUsers";
		 */
	}
	
	

//	@RequestMapping(value = "/registerAsVisitor", method = RequestMethod.POST)
//	public ModelAndView registerUserAccount(@ModelAttribute("visitorForm") @Valid VisitorForm visitorForm,
//			BindingResult result, WebRequest request, Errors errors) {
//
//		Visitor registered = new Visitor();
//		if (!result.hasErrors()) {
//			registered = createVisitorAccount(visitorForm, result);
//		}
//		if (registered == null) {
//			result.rejectValue("email", "message.regError");
//		}
//		if (result.hasErrors()) {
//			if(result.hasGlobalErrors()) {
//				System.out.println("baa");
//			}
//			return new ModelAndView("registerVisitor.jsp", "user", visitorForm);
//		} else {
//			return new ModelAndView("thanksForRegistration.jsp", "user", visitorForm);
//		}
//		
//		/*
//		 * if(result.hasErrors()){
//			return "register";
//		}
//		userService.createUser(user);
//		return "redirect:/listUsers";
//		 */
//	}

	private Visitor createVisitorAccount(VisitorForm visitorForm, BindingResult result) {
		Visitor registered = null;
		try {
			registered = visitorService.registerNewVisitorAccount(visitorForm);
		} catch (EmailExistsException e) {
			return null;
		}
		return registered;
	}

}
