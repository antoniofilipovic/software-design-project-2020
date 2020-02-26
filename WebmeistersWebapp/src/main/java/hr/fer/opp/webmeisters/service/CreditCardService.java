package hr.fer.opp.webmeisters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.opp.webmeisters.dao.CreditCardRepository;
import hr.fer.opp.webmeisters.data.form.CreditCardForm;
import hr.fer.opp.webmeisters.data.model.CreditCard;
import hr.fer.opp.webmeisters.data.model.Organiser;
@Service
public class CreditCardService {
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	@Autowired
	private OrganiserService organiserService;

	public void addCreditCardForOrganiser(Organiser organiser,CreditCardForm creditCardForm) {
		CreditCard creditCard =new CreditCard();
		creditCard.setCardNumber(creditCardForm.getCardNumber());
		creditCard.setCardOwner(creditCardForm.getCardOwner());
		creditCardRepository.save(creditCard);
		organiserService.addCreditCard(creditCard,organiser);
		
	}

	public CreditCard getCreditCard(int parseInt) {
		return creditCardRepository.getOne(parseInt);
	}

	public void deleteCreditCard(Organiser organiser, CreditCard creditCard) {
		organiserService.deleteCreditCardForOrganiser(organiser);
		creditCardRepository.delete(creditCard);
		
	}
	

}
