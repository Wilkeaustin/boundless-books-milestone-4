package com.boundlessbooks.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.boundlessbooks.controller..*(..)) || "
            + "execution(* com.boundlessbooks.service..*(..)) || "
            + "execution(* com.boundlessbooks.repository..*(..))")
    public Object logMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        Class<?> className = joinPoint.getSignature().getDeclaringType();
        String methodName = joinPoint.getSignature().getName();
        Logger logger = LoggerFactory.getLogger(className);

        logger.info("ENTER: {}.{}", className.getSimpleName(), methodName);

        try {
            Object result = joinPoint.proceed();

            logger.info("EXIT: {}.{}", className.getSimpleName(), methodName);
            return result;
        } catch (Throwable exception) {
            logger.error(
                    "EXCEPTION: {}.{} - {}",
                    className.getSimpleName(),
                    methodName,
                    exception.getMessage(),
                    exception
            );

            throw exception;
        }
    }
}