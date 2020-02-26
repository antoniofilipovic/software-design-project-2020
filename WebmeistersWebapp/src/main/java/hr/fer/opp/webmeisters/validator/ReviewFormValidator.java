package hr.fer.opp.webmeisters.validator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hr.fer.opp.webmeisters.data.form.EventForm;
import hr.fer.opp.webmeisters.data.form.ReviewForm;

@Component
public class ReviewFormValidator implements Validator {
	

	 @Override
	    public boolean supports(Class<?> aClass) {
	        return ReviewForm.class.equals(aClass);
	    }

	    @Override
	    public void validate(Object o, Errors errors) {
	        ReviewForm eventForm = (ReviewForm) o;
	        
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "NotEmpty");
	        

			



	        
		// TODO Auto-generated method stub
		
	}

}
