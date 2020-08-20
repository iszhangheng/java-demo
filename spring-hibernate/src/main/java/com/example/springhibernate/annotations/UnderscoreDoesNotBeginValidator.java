package com.example.springhibernate.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UnderscoreDoesNotBeginValidator implements ConstraintValidator<UnderscoreDoesNotBegin, String> {
    @Override
    public void initialize(UnderscoreDoesNotBegin constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || !value.startsWith("_");
    }
}
