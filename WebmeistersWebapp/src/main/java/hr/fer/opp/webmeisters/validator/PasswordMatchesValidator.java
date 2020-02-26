package hr.fer.opp.webmeisters.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import hr.fer.opp.webmeisters.annotations.PasswordMatches;
import hr.fer.opp.webmeisters.data.form.OrganiserForm;
import hr.fer.opp.webmeisters.data.form.VisitorForm;

public class PasswordMatchesValidator 
implements ConstraintValidator<PasswordMatches, Object> { 
   
  @Override
  public void initialize(PasswordMatches constraintAnnotation) {       
  }
  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context){  
	  if(obj instanceof VisitorForm) {

	      VisitorForm user = (VisitorForm) obj;
	      return user.getPassword().equals(user.getRePassword());  
	  }  
	  else {

	      OrganiserForm user = (OrganiserForm) obj;
	      return user.getPassword().equals(user.getRepassword());  
	  }
  }     
}