package hr.fer.opp.webmeisters.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hr.fer.opp.webmeisters.data.form.EventForm;
import hr.fer.opp.webmeisters.data.form.OrganiserForm;
import hr.fer.opp.webmeisters.data.form.VisitorForm;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class EventFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return EventForm.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		EventForm eventForm = (EventForm) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "start", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stop", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");
		if (eventForm.getCategory().equals("Izaberi kategoriju")) {
			errors.rejectValue("category", "Choose.Category");
		}
		if (eventForm.getArea().equals("Izaberi područje")) {
			errors.rejectValue("area", "Choose.Area");
		}

		if (errors.hasErrors()) {
			return;
		}

		if (!eventForm.getStart().isEmpty() && !eventForm.getStop().isEmpty()) {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			LocalDateTime dateTimeStart = LocalDateTime.parse(eventForm.getStart(), format);
			LocalDateTime dateTimeStop = LocalDateTime.parse(eventForm.getStop(), format);

			if (dateTimeStart.isAfter(dateTimeStop)) {
				errors.rejectValue("stop", "EndBeforeStart.event");
			}

			if (dateTimeStart.isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
				errors.rejectValue("start", "StartBeforeNow.event");
			}
		}

		// TODO Auto-generated method stub

	}

}
