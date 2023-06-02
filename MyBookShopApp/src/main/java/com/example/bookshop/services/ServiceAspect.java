package com.example.bookshop.services;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Log
@Aspect
@Component
public class ServiceAspect {

    @Pointcut(value = "execution(public * com.example.bookshop.services.*Service.find*(..))")
    public void callFindMethods() {}

    @Before(value = "callFindMethods()")
    public void beforeCallFindMethods(JoinPoint aJoinPoint) {
        log.info("calling find method - " + aJoinPoint.getSignature());
    }

    @AfterReturning(value = "callFindMethods()", returning = "aReturnValue")
    public void afterCallFindMethod(JoinPoint aJoinPoint, Object aReturnValue) {
        log.fine(aJoinPoint.getSignature() + " return values - " + aReturnValue.toString());
    }

}
