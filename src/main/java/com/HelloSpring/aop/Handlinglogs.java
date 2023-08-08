package com.HelloSpring.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class Handlinglogs {
    @Around("execution(* com.HelloSpring.controller..*(..))")
    public Object displayTime(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalDateTime startlocalDateTime = LocalDateTime.now();
        log.info("Start Time :" + startlocalDateTime);
        Object result = joinPoint.proceed();
        LocalDateTime endLocalDateTime = LocalDateTime.now();
        log.info("End time:" + endLocalDateTime);
        Duration timeTaken = Duration.between(startlocalDateTime, endLocalDateTime);
        long hours = timeTaken.toHours();
        long minutes = timeTaken.toHours() % 60;
        long second = timeTaken.getSeconds() % 60;
        long nanoSec = timeTaken.getNano() % 60;
        log.info("Method : " + joinPoint.getSignature().getName()
                + ", Execution complete at  : " + endLocalDateTime
                + " Time taken = " + hours + " hours "
                + minutes + " minutes "
                + second + " seconds "
                + nanoSec + " nanoSecond");
        return result;
    }

}
