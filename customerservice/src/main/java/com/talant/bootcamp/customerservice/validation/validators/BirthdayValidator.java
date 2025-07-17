package com.talant.bootcamp.customerservice.validation.validators;


import com.talant.bootcamp.customerservice.validation.annotations.ValidBirthday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class BirthdayValidator implements ConstraintValidator<ValidBirthday, LocalDate> {


    @Override
    public void initialize(ValidBirthday constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate birthday, ConstraintValidatorContext constraintValidatorContext) {
        if (birthday == null){
            return true;
        }
        return LocalDate.now().minusYears(birthday.getYear()).getYear() >= 18;
    }
}
