package com.HelloSpring.aop;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FrequencyValidateWRTDays.class)
public @interface ValidFrequencyWRTDays {

    String message() default "Invalid Request Body";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
