package com.apress.prospring6.eleven.validator;

import com.apress.prospring6.eleven.domain.Singer;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountrySingerValidator implements ConstraintValidator<CheckCountrySinger, Singer> {

    @Override
    public void initialize(CheckCountrySinger constraintAnnotation) {}

    @Override
    public boolean isValid(Singer singer, ConstraintValidatorContext constraintValidatorContext) {
        return singer.getGenre() == null || (!singer.isCountrySinger() ||
                (singer.getLastName()!=null && singer.getGender()!=null));
    }
}
