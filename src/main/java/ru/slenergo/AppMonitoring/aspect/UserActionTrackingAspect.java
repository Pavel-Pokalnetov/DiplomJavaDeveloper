package ru.slenergo.AppMonitoring.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class UserActionTrackingAspect {
    @Around("execution (public * ru.slenergo.AppMonitoring..*.*(..))")
    public Object trackUserAction(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        System.out.print("Вызов метода: " + joinPoint.getSignature().getName() + ", аргументы: " + args.toString());
        long runtime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        runtime = System.currentTimeMillis() - runtime;
        System.out.printf(", выполнено за :%d мс\n", runtime);
        return result;
    }
}
