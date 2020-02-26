package hr.fer.opp.webmeisters.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutControler {

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutGetMethod(Model model, HttpServletRequest req) {

        req.getSession().removeAttribute("loggedInUser");

        return "home";

    }
}
