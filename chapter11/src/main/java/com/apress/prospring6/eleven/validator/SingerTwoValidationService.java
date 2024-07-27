package com.apress.prospring6.eleven.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("singerTwoValidationService")
public class SingerTwoValidationService {

    private final Validator validator;

    public SingerTwoValidationService(Validator validator) {
        this.validator = validator;
    }

    public Set<ConstraintViolation<SingerTwo>> validate(SingerTwo singer) {
        return validator.validate(singer);
    }
}
