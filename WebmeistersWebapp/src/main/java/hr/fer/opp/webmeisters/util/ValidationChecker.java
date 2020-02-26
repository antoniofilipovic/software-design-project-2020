package hr.fer.opp.webmeisters.util;

import java.util.Date;

import org.springframework.ui.Model;

import hr.fer.opp.webmeisters.data.model.Organiser;

public class ValidationChecker {
	
	public static void validateOrganiser(Organiser organiser,Model model) {
		model.addAttribute("isValid", organiser.getAccountValidUntil().before(new Date())?"false":"true");
	}

}
