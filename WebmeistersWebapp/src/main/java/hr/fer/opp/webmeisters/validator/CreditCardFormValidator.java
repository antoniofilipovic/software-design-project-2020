package hr.fer.opp.webmeisters.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hr.fer.opp.webmeisters.data.form.CreditCardForm;
@Component
public class CreditCardFormValidator implements Validator {
	
	@Override
    public boolean supports(Class<?> aClass) {
        return CreditCardForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	CreditCardForm creditCardForm = (CreditCardForm) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cardNumber", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cardOwner", "NotEmpty");

		if(errors.hasErrors()){
			return;
		}
		String cardNumber=creditCardForm.getCardNumber();
		for(int i=0;i<cardNumber.length();i++) {
			if(!Character.isDigit(cardNumber.charAt(i))) {
				errors.rejectValue("cardNumber", "CreditCard.Number");
				break;
			}
		}
		if(errors.hasErrors()){
			return;
		}
		if(cardNumber.length()!=16) {
			errors.rejectValue("cardNumber", "CreditCard.Size");

		}
	

      



        
	// TODO Auto-generated method stub
	
}

}
