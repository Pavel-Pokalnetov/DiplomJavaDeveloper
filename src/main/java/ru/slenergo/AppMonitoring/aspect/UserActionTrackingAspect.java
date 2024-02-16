package ru.slenergo.AppMonitoring.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class UserActionTrackingAspect {
    @Around("execution(public * ru.slenergo.AppMonitoring..*.*(..))")
    public Object trackUserAction(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String arguments = Arrays.toString(args);
        System.out.println(" > Вызов метода: "
                + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName() + ", аргументы: "
                + arguments);
        return joinPoint.proceed();
    }
}
