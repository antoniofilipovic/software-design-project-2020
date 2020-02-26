package hr.fer.opp.webmeisters.controllers;

import hr.fer.opp.webmeisters.data.form.LoginForm;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.service.GetUserTypeService;
import hr.fer.opp.webmeisters.service.LoginService;
import hr.fer.opp.webmeisters.validator.LoginFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

	
	@Autowired
	private LoginService loginService;
	
	@Autowired 
	private LoginFormValidator loginFormValidator;
	


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getHomeAndLoginMethod(Model model, HttpServletRequest req) {
		LoginForm loginForm = new LoginForm();
	    model.addAttribute("loginForm", loginForm);
	   
		
		return "login";
	}


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView postHomeAndLoginMethod(@ModelAttribute("loginForm") LoginForm loginForm, BindingResult result, Model model,HttpServletRequest req) {
		
		loginFormValidator.validate(loginForm, result);

		
		if (!result.hasErrors()) {
			
			loginUser(loginForm,result,req);
		}
	
		if (result.hasErrors()) {
			
			return new ModelAndView("login", "loginForm", loginForm);
			
		} else {
			return new ModelAndView("redirect:/home", "",null);
		}
		
	
	}

	private void loginUser(LoginForm loginForm,BindingResult result,HttpServletRequest req) {

		User user=loginService.userAccountExists(loginForm);
		if(user==null) {
			//System.out.println(result.getErrorCount());
			result.rejectValue("password", "Wrong.login");
			result.addError(new ObjectError("password", "Wrong.login"));
			//System.out.println("dodali smo password gresku");
			//System.out.println(result.getErrorCount());
			return;
		}
		
		User newUser=loginService.registerUser(user);
		

		req.getSession().setAttribute("loggedInUser", newUser);

		if(newUser.getFullName().equals("admin")){
			req.getSession().setAttribute("userType", "admin");
		} else {
			req.getSession().setAttribute("userType", GetUserTypeService.getType(newUser));
		}


	
	}
	



}
