package hr.fer.opp.webmeisters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.PayPalRepository;
import hr.fer.opp.webmeisters.data.form.PayPalForm;
import hr.fer.opp.webmeisters.data.model.CreditCard;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.PayPal;
@Service
public class PayPalService {
	@Autowired
	private PayPalRepository payPalRepository;
	
	@Autowired
	private OrganiserService organiserService;
	
	

	public void addPayPalForOrganiser(Organiser organiser, PayPalForm payPalForm) {
		PayPal payPal =new PayPal();
		payPal.setPayPalEmail(payPalForm.getPayPalEmail());
		payPalRepository.save(payPal);
		organiserService.addPayPal(payPal,organiser);
		
	}



	public PayPal getPayPal(int parseInt) {
		return payPalRepository.getOne(parseInt);
	}



	public void deletePayPal(Organiser organiser, PayPal payPal) {
		organiserService.deletePayPalForOrganiser(organiser);
		payPalRepository.delete(payPal);
		
	}

}
