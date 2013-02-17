package com.zagyvaib.example.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Date;

@Aspect
public class ExampleAspect {

    @Before("voidMethodsOfTheExampleInterface()")
    public void timePrintingAdvice() {
        System.out.println("FYI, the exact time is: " + new Date());
    }

    @Around("intReturningMethodsOfTheExampleInterface()")
    public Object incrementIntResultAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        return (int) joinPoint.proceed() + 1;
    }

    @Pointcut("execution(void ExampleInterface.*(..))")
    private void voidMethodsOfTheExampleInterface() {}

    @Pointcut("execution(int ExampleInterface.*(..))")
    private void intReturningMethodsOfTheExampleInterface() {}
}
