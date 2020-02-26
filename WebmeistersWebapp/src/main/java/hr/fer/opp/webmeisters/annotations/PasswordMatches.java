package hr.fer.opp.webmeisters.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import hr.fer.opp.webmeisters.validator.PasswordMatchesValidator;

@Target(value = { ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches { 
    String message() default "Passwords don't match";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}