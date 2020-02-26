package hr.fer.opp.webmeisters.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hr.fer.opp.webmeisters.data.form.CreditCardForm;
import hr.fer.opp.webmeisters.data.form.SubscriptionForm;

@Component
public class SubscriptionFormValidator implements Validator {
	
	@Override
    public boolean supports(Class<?> aClass) {
        return SubscriptionForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	SubscriptionForm subscriptionForm = (SubscriptionForm) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subscription", "NotEmpty");

		if(errors.hasErrors()){
			return;
		}
		String subscirption=subscriptionForm.getSubscription();
		for(int i=0;i<subscirption.length();i++) {
			if(!Character.isDigit(subscirption.charAt(i))) {
				errors.rejectValue("subscription", "Subscription.Number");
				break;
			}
		}
		if(errors.hasErrors()){
			return;
		}
		try {
			double subscription=Double.parseDouble(subscirption);
		}catch(NumberFormatException e) {
			errors.rejectValue("subscription", "Subscription.NumberFormat");

		}
		
	

      



        
	// TODO Auto-generated method stub
	
}

}
