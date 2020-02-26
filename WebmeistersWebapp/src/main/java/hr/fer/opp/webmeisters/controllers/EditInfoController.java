package hr.fer.opp.webmeisters.controllers;

import hr.fer.opp.webmeisters.data.form.EditInfoOrganiserForm;
import hr.fer.opp.webmeisters.data.form.EditInfoVisitorForm;
import hr.fer.opp.webmeisters.data.form.OrganiserForm;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;
import hr.fer.opp.webmeisters.exception.EmailExistsException;
import hr.fer.opp.webmeisters.service.GetUserTypeService;
import hr.fer.opp.webmeisters.service.OrganiserService;
import hr.fer.opp.webmeisters.service.VisitorService;
import hr.fer.opp.webmeisters.validator.EditOrganiserInfoValidator;
import hr.fer.opp.webmeisters.validator.EditVisitorInfoValidator;
import hr.fer.opp.webmeisters.validator.OrganiserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class EditInfoController {

    @Autowired
    private OrganiserService organiserService;
    
    @Autowired
    private EditOrganiserInfoValidator editOrganiserInfoValidator;
    
    @Autowired
    private VisitorService visitorService;
    
    @Autowired
    private EditVisitorInfoValidator editVisitorInfoValidator;

   

    @RequestMapping(value ="/editInfo/organiser/{id}", method = RequestMethod.GET)
    public String editOrganiserInformationGetMethod(@PathVariable String id,HttpServletRequest request, Model model,@SessionAttribute("loggedInUser") User user  ) {
        Organiser organiser = organiserService.findById(user.getId());
        
        if(organiser == null){
           //odjebi ga
        	return "home";
        }
        EditInfoOrganiserForm editInfoOrganiserForm = new EditInfoOrganiserForm();
        editInfoOrganiserForm.fillData(organiser);
        model.addAttribute("editedOrganiser", editInfoOrganiserForm);
        model.addAttribute("id", id);


        return "profileOrganiserEdit";
    }

    @RequestMapping(value ="/editInfo/organiser/{id}", method = RequestMethod.POST)
    public ModelAndView editOrganiserInformationPostMethod(@PathVariable String id,@ModelAttribute("editedOrganiser") EditInfoOrganiserForm editInfoOrganiserForm,
            BindingResult result, Model model, HttpServletRequest request,@SessionAttribute("loggedInUser") User user  ) {
    	
    	
    	Organiser organiser = organiserService.findById(user.getId());
    	editOrganiserInfoValidator.setOrganiser(organiser);
    	editOrganiserInfoValidator.validate(editInfoOrganiserForm, result);

		if (!result.hasErrors()) {
			Organiser updatedOrganiser = updateOrganiserAccount(editInfoOrganiserForm,organiser);
			request.getSession().setAttribute("loggedInUser", updatedOrganiser);
			request.getSession().setAttribute("userType", GetUserTypeService.getType(updatedOrganiser));
			System.out.println(GetUserTypeService.getType(updatedOrganiser));
		}
	

		if (result.hasErrors()) {
			model.addAttribute("id", id);
			return new ModelAndView("profileOrganiserEdit", "organsier", editInfoOrganiserForm);
		} else {
			return new ModelAndView("redirect:/manageAccount/organiser/"+id, "organiser", editInfoOrganiserForm);
		}
       

    }

    @RequestMapping(value ="/editInfo/admin/{id}", method = RequestMethod.POST)
    public ModelAndView editAdminInformationPostMethod(@PathVariable String id,@ModelAttribute("editedOrganiser") EditInfoOrganiserForm editInfoOrganiserForm,
                                                           BindingResult result, Model model, HttpServletRequest request,@SessionAttribute("loggedInUser") User user  ) {

        return new ModelAndView("profileAdmin");

    }
    
    
    
    @RequestMapping(value ="/editInfo/visitor/{id}", method = RequestMethod.GET)
    public String editVisitorInformationGetMethod(@PathVariable String id,HttpServletRequest request, Model model,@SessionAttribute("loggedInUser") User user  ) {
        Visitor visitor = visitorService.findById(user.getId());
        
        if(visitor == null){
           //odjebi ga
        	return "home";
        }
        EditInfoVisitorForm editInfoVisitorForm = new EditInfoVisitorForm();
        editInfoVisitorForm.fillData(visitor);
        model.addAttribute("editedVisitor", editInfoVisitorForm);
        model.addAttribute("id", id);


        return "profileVisitorEdit";
    }

    @RequestMapping(value ="/editInfo/visitor/{id}", method = RequestMethod.POST)
    public ModelAndView editVisitorInformationPostMethod(@PathVariable String id,@ModelAttribute("editedVisitor") EditInfoVisitorForm editInfoVisitorForm,
            BindingResult result, Model model, HttpServletRequest request,@SessionAttribute("loggedInUser") User user  ) {
    	
    	
        Visitor visitor = visitorService.findById(user.getId());

    	editVisitorInfoValidator.setVisitor(visitor);
    	editVisitorInfoValidator.validate(editInfoVisitorForm, result);

		if (!result.hasErrors()) {
			Visitor updatedVisitor = updateVisitorAccount(editInfoVisitorForm,visitor);
			request.getSession().setAttribute("loggedInUser", updatedVisitor);
			request.getSession().setAttribute("userType", GetUserTypeService.getType(updatedVisitor));
			System.out.println(GetUserTypeService.getType(updatedVisitor));
		}
	

		if (result.hasErrors()) {
			model.addAttribute("id", id);
			return new ModelAndView("profileVisitorEdit", "visitor", editInfoVisitorForm);
		} else {
			return new ModelAndView("redirect:/manageAccount/visitor/"+id, "visitor", editInfoVisitorForm);
		}
       

    }

    public Organiser updateOrganiserAccount(EditInfoOrganiserForm organiserForm,Organiser organiser) {
        Organiser registered = null;

        try {
            registered = organiserService.updateOrganiserAccount(organiser,organiserForm);


        } catch (EmailExistsException ex) {
            // do nothings
        }
        return registered;
    }
    
    
    public Visitor updateVisitorAccount(EditInfoVisitorForm visitorForm,Visitor visitor) {
        Visitor registered = null;

        try {
            registered = visitorService.updateVisitorAccount(visitor,visitorForm);


        } catch (EmailExistsException ex) {
            // do nothings
        }
        return registered;
    }
}
