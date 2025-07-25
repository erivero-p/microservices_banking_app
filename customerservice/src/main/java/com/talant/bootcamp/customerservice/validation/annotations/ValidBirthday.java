package com.talant.bootcamp.customerservice.validation.annotations;

import com.talant.bootcamp.customerservice.validation.validators.BirthdayValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BirthdayValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidBirthday {
    String message() default "Customer's age must be over 18.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
