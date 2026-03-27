package com.jacobhampton.techtest.shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

/**
 * Simple reflection based validator to just loop through the fields and make sure at least one isn't null
 */
public class AtLeastOneFieldValidator implements ConstraintValidator<AtLeastOneField, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return Arrays.stream(value.getClass().getDeclaredFields())
                .anyMatch(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(value) != null;
                    } catch (IllegalAccessException exception) {
                        return false;
                    }
                });
    }

}
