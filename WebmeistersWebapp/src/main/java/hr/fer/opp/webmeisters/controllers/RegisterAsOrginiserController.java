package hr.fer.opp.webmeisters.controllers;

import hr.fer.opp.webmeisters.dao.VisitorRepository;
import hr.fer.opp.webmeisters.data.form.OrganiserForm;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.exception.EmailExistsException;
import hr.fer.opp.webmeisters.service.GetUserTypeService;
import hr.fer.opp.webmeisters.service.OrganiserService;
import hr.fer.opp.webmeisters.validator.OrganiserFormValidator;
import hr.fer.opp.webmeisters.validator.VisitorFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisterAsOrginiserController {

	@Autowired
	private OrganiserService orgService;
	
	@Autowired
    private OrganiserFormValidator organiserFormValidator;
	

	

	@RequestMapping(value = "/registerAsOrganiser", method = RequestMethod.GET)
	public String getHomeAndLoginMethod(WebRequest request, Model model) {

		OrganiserForm organiserForm = new OrganiserForm();
		model.addAttribute("organiserForm", organiserForm);
		return "registerOrginiser";
	}

	@RequestMapping(value = "/registerAsOrganiser", method = RequestMethod.POST)
	public ModelAndView registerUserAccount(@ModelAttribute("organiserForm") OrganiserForm organiserForm,
                                            BindingResult result, Model model, HttpServletRequest req) {

		organiserFormValidator.validate(organiserForm, result);


		if (!result.hasErrors()) {
			Organiser org = createOrganiserAccount(organiserForm, result);
			req.getSession().setAttribute("loggedInUser", org);
			req.getSession().setAttribute("userType", GetUserTypeService.getType(org));
			System.out.println(GetUserTypeService.getType(org));
		}

		if (result.hasErrors()) {

			return new ModelAndView("registerOrginiser", "organsier", organiserForm);
		} else {
			return new ModelAndView("thanksForRegistration", "organiser", organiserForm);
		}
	}

	public Organiser createOrganiserAccount(OrganiserForm orgForm, BindingResult bindingResult) {
		Organiser registered = null;

		try {
			registered = orgService.registerNewOrganiserAccount(orgForm);


		} catch (EmailExistsException ex) {
			// do nothings
		}
		return registered;
	}
}
