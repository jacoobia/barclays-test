package com.jacobhampton.techtest.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Shoutout to baeldung for the syntax for a validatior annotation
 * https://www.baeldung.com/spring-mvc-custom-validator#:~:text=%40Documented%0A%40Constraint(validatedBy,payload()%20default%20%7B%7D%3B%0A%7D
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneFieldValidator.class)
public @interface AtLeastOneField {
    String message() default "At least one field must be provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
