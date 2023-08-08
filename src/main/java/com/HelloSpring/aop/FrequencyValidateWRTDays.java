package com.HelloSpring.aop;


import com.HelloSpring.GlobalException.ResourceNotFoundException;
import com.HelloSpring.dto.request.RequestBodyDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FrequencyValidateWRTDays implements ConstraintValidator<ValidFrequencyWRTDays, RequestBodyDto> {
              @Override
        public boolean isValid(RequestBodyDto requestBodyDto, ConstraintValidatorContext context) {
            String frequency=requestBodyDto.getFrequency();
            int days=requestBodyDto.getDay();

            if(frequency==null ||(!frequency.equalsIgnoreCase(String.valueOf(Frequency.DAILY)) &&   !frequency.equalsIgnoreCase(String.valueOf(Frequency.MONTHLY)))) {
                try {
                    throw new ResourceNotFoundException("Frequency Value is not Valid");
                } catch (ResourceNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if(frequency.equalsIgnoreCase(String.valueOf(Frequency.MONTHLY)) && ( days<1 || days>31)){
                try {
                    throw new ResourceNotFoundException("Days are not valid.........");
                } catch (ResourceNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        }
    }


/**
 * Implements the validation logic.
 * The state of {@code value} must not be altered.
 * <p>
 * This method can be accessed concurrently, thread-safety must be ensured
 * by the implementation.
 *
 * @param value   object to validate
 * @param context context in which the constraint is evaluated
 * @return {@code false} if {@code value} does not pass the constraint
 */
