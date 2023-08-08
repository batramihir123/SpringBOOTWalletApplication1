package com.HelloSpring.aop;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = CustomerIdValidator.class)
@Documented
public @interface ValidateCustomerId {
    String message() default "Invalid Customer ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
