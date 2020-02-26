package hr.fer.opp.webmeisters.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hr.fer.opp.webmeisters.data.form.NotificationForm;
import hr.fer.opp.webmeisters.data.form.SubscriptionForm;

@Component
public class NotificationFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return NotificationForm.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		NotificationForm notificationForm = (NotificationForm) o;

		if (notificationForm.getCategories().isEmpty()) {
			errors.rejectValue("categories", "Choose.Categories");
		}
		if (notificationForm.getAreas().isEmpty()) {
			errors.rejectValue("areas", "Choose.Areas");
		}

	}

}
