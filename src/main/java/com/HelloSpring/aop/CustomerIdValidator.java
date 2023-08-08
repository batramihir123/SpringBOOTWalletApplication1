package com.HelloSpring.aop;


import com.HelloSpring.GlobalException.ResourceNotFoundException;
import com.HelloSpring.repo.CustomerRepo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class CustomerIdValidator implements ConstraintValidator<ValidateCustomerId,Integer>
{
    @Autowired
    CustomerRepo customerRepository;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

        log.info("...............1"+value);
        if (value == null) {
            throw new ResourceNotFoundException("Customer ID is null");
        }

        System.out.println("..........................3.");
        if(customerRepository.existsByCustomerId(value)==Boolean.FALSE){
            throw new ResourceNotFoundException("Customer id is not present");
        }
        return true;
    }

//    @Override
//    public boolean isValid(Integer customerId, ConstraintValidatorContext context) {
//        log.info("...............1"+customerId);
//        if (customerId == null) {
//            throw new ResourceNotFoundException("Customer ID is null");
//        }
//
//        System.out.println("...........................");
//        if(customerRepository.existsByCustomerId(customerId)==Boolean.FALSE){
//            throw new ResourceNotFoundException("Customer id is not present");
//        }
//        return true;
//    }
}

//    @Before("execution(* com.HelloSpring.controller..*(..))")
//    public void validateCustomerId(JoinPoint joinPoint) throws NoSuchMethodException, ResourceNotFoundException {
//        Method method = getMethod(joinPoint);
//        Object[] methodArgs = joinPoint.getArgs();
//
//        int customerIdIndex = -1;
//        Parameter[] parameters = method.getParameters();
//        for (int i = 0; i < parameters.length; i++) {
//            Parameter parameter = parameters[i];
//            if (isCustomerIdParameter(parameter) && parameter.isAnnotationPresent(PathVariable.class) && methodArgs[i] instanceof Integer) {
//                customerIdIndex = i;
//                break;
//            }
//        }
//        if (customerIdIndex != -1) {
//            int customerID = (int) methodArgs[customerIdIndex];
//            if (customerRepository.existsByCustomerId(customerID) == Boolean.FALSE) {
//                throw new ResourceNotFoundException("The Customer Id is not present");
//            }
//        }
//    }
//
//    private boolean isCustomerIdParameter(Parameter parameter) {
//        return "customerId".equals(parameter.getName());
//    }
//
//    private Method getMethod(JoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        return methodSignature.getMethod();
//    }