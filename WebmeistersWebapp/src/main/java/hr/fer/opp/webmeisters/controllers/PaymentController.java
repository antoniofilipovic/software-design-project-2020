package hr.fer.opp.webmeisters.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hr.fer.opp.webmeisters.data.form.CreditCardForm;
import hr.fer.opp.webmeisters.data.form.EventForm;
import hr.fer.opp.webmeisters.data.form.LoginForm;
import hr.fer.opp.webmeisters.data.form.PayPalForm;
import hr.fer.opp.webmeisters.data.form.ReviewForm;
import hr.fer.opp.webmeisters.data.model.CreditCard;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.PayPal;
import hr.fer.opp.webmeisters.service.AdminService;
import hr.fer.opp.webmeisters.service.CreditCardService;
import hr.fer.opp.webmeisters.service.OrganiserService;
import hr.fer.opp.webmeisters.service.PayPalService;
import hr.fer.opp.webmeisters.validator.CreditCardFormValidator;
import hr.fer.opp.webmeisters.validator.PayPalFormValidator;

@Controller
public class PaymentController {

	@Autowired
	private OrganiserService organiserService;

	@Autowired
	private CreditCardFormValidator creditCardFormValidator;

	@Autowired
	private PayPalFormValidator payPalFormValidator;

	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private PayPalService payPalService;

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "/payment/{id}", method = RequestMethod.GET)
	public String paymentGetMethod(@PathVariable String id, Model model,
			@ModelAttribute("paymentTry") String paymentTry, @ModelAttribute("paymentSuccess") String paymentSuccess) {
		if (paymentTry == null || paymentTry.isEmpty()) {

			model.addAttribute("paymentTry", "false");
		} else {
			model.addAttribute("paymentSuccess", paymentSuccess);
		}
		// model.addAttribute("paymentTry", "false");

		Organiser organiser = organiserService.findById(Integer.parseInt(id));
		addToModelForOrganiser(model, organiser);
		model.addAttribute("subscription", adminService.getAdmin().getSubscription());
		return "/payment";
	}

	@RequestMapping(value = "/payment/pay/{id}", method = RequestMethod.POST)
	public String paymentPayPostMethod(@PathVariable String id, Model model,
			@RequestParam(name = "paymentType") String paymentType, RedirectAttributes attributes,HttpSession session) {
		// model.addAttribute("paymentTry", "true");
		attributes.addFlashAttribute("paymentTry", "true");

		Organiser organiser = organiserService.findById(Integer.parseInt(id));
		// addToModelForOrganiser(model, organiser);

		// Generate random number
		double rand = Math.random();

		// Output is different everytime this code is executed
		System.out.println("Random Number:" + rand);
		if (rand <= 0.5) {
			attributes.addFlashAttribute("paymentSuccess", "Plaćanje neuspješno provedeno.");
			// model.addAttribute("paymentSuccess", "Plaćanje neuspješno provedeno.");

		} else {
			attributes.addFlashAttribute("paymentSuccess", "Plaćanje uspješno provedeno.");
			LocalDate dateValidTo = Instant.ofEpochMilli(organiser.getAccountValidUntil().getTime())
					.atZone(ZoneId.systemDefault()).toLocalDate();
			organiser.setAccountValidUntil(
					Date.from((dateValidTo.plusMonths(1)).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
			organiserService.update(organiser);
			session.setAttribute("loggedInUser", organiser);
			// model.addAttribute("paymentSuccess", "Plaćanje " + paymentType + " uspješno
			// provedeno");
		}
		model.addAttribute("subscription", adminService.getAdmin().getSubscription());
		return "redirect:/payment/" + id;
	}

	@RequestMapping(value = "/payment/view/{id}", method = RequestMethod.GET)
	public String paymentViewGetMethod(@PathVariable String id, Model model) {
		Organiser organiser = organiserService.findById(Integer.parseInt(id));
		model.addAttribute("organiser", organiser);

		addToModelForOrganiser(model, organiser);

		return "/paymentView";
	}

	@RequestMapping(value = "/payment/addCreditCard/{id}", method = RequestMethod.GET)
	public String paymentAddCreditCardGetMethod(@PathVariable String id, Model model) {
		Organiser organiser = organiserService.findById(Integer.parseInt(id));
		model.addAttribute("organiser", organiser);
		model.addAttribute("creditCardForm", new CreditCardForm());

		return "addCreditCard";
	}

	@RequestMapping(value = "/payment/addCreditCard/{id}", method = RequestMethod.POST)
	public String paymentAddCreditCardPostMethod(@PathVariable String id, Model model,
			@ModelAttribute("creditCardForm") CreditCardForm creditCardForm, BindingResult result) {
		Organiser organiser = organiserService.findById(Integer.parseInt(id));
		model.addAttribute("organiser", organiser);

		creditCardFormValidator.validate(creditCardForm, result);

		if (result.hasErrors()) {

			return "addCreditCard";
		} else {
			creditCardService.addCreditCardForOrganiser(organiser, creditCardForm);
			return "redirect:/payment/view/" + id;
		}

	}

	@RequestMapping(value = "/payment/addPayPal/{id}", method = RequestMethod.GET)
	public String paymentAddPayPalGetMethod(@PathVariable String id, Model model) {
		Organiser organiser = organiserService.findById(Integer.parseInt(id));
		model.addAttribute("organiser", organiser);
		model.addAttribute("payPalForm", new PayPalForm());

		return "addPayPal";
	}

	@RequestMapping(value = "/payment/addPayPal/{id}", method = RequestMethod.POST)
	public String paymentAddPayPalPostMethod(@PathVariable String id, Model model,
			@ModelAttribute("payPalForm") PayPalForm payPalForm, BindingResult result) {
		Organiser organiser = organiserService.findById(Integer.parseInt(id));
		model.addAttribute("organiser", organiser);

		payPalFormValidator.validate(payPalForm, result);

		if (result.hasErrors()) {
			return "addPayPal";
		} else {
			payPalService.addPayPalForOrganiser(organiser, payPalForm);
			return "redirect:/payment/view/" + id;
		}

	}

	@RequestMapping(value = "/payment/deletePayPal/{userId}/{payPalId}", method = RequestMethod.GET)
	public String paymentDeletePayPalGetMethod(@PathVariable String userId, @PathVariable String payPalId,
			Model model) {
		Organiser organiser = organiserService.findById(Integer.parseInt(userId));
		PayPal payPal = payPalService.getPayPal(Integer.parseInt(payPalId));
		model.addAttribute("organiser", organiser);
		model.addAttribute("payPalForm", new PayPalForm());
		payPalService.deletePayPal(organiser, payPal);
		return "redirect:/payment/view/" + userId;
	}

	@RequestMapping(value = "/payment/deleteCreditCard/{userId}/{creditCardId}", method = RequestMethod.GET)
	public String paymentDeleteCreditCardGetMethod(@PathVariable String userId, @PathVariable String creditCardId,
			Model model) {
		Organiser organiser = organiserService.findById(Integer.parseInt(userId));
		CreditCard creditCard = creditCardService.getCreditCard(Integer.parseInt(creditCardId));
		creditCardService.deleteCreditCard(organiser, creditCard);
		return "redirect:/payment/view/" + userId;
	}

	private void addToModelForOrganiser(Model model, Organiser organiser) {
		model.addAttribute("organiser", organiser);
		PayPal paypalAcc = organiser.getPayPal();
		CreditCard creditCard = organiser.getCreditCard();
		List<String> payments = new ArrayList<>();
		if (paypalAcc != null) {
			model.addAttribute("hasPaypal", "true");
			model.addAttribute("paypal", paypalAcc);
			payments.add("Paypal racun");
		} else {
			model.addAttribute("hasPaypal", "false");
		}

		if (creditCard != null) {
			model.addAttribute("hasCreditCard", "true");
			model.addAttribute("creditCard", creditCard);
			payments.add("Kreditna kartica");
		} else {
			model.addAttribute("hasCreditCard", "false");
		}
		if (payments.size() == 0) {
			model.addAttribute("noPayments", "true");
		} else {
			model.addAttribute("noPayments", "false");
		}
		model.addAttribute("payments", payments);

	}
}
