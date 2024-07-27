package com.apress.prospring6.eleven.validator;

import com.apress.prospring6.eleven.domain.Blogger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("bloggerValidator")
public class BloggerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Blogger.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var b = (Blogger) target;
        if(StringUtils.isEmpty(b.firstName())&&StringUtils.isEmpty(b.lastName())){
            errors.rejectValue("firstName","firstNameOrLastName.required");
            errors.rejectValue("lastName","firstNameOrLastName.required");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"firstName","field.required");
    }

}
