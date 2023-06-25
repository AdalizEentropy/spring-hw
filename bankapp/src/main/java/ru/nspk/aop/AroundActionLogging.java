package ru.nspk.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AroundActionLogging {

    @Around("@annotation(ru.nspk.aop.Loggable)")
    public Object action(ProceedingJoinPoint joinPoint) throws Throwable {
        before(joinPoint);
        var result = joinPoint.proceed();
        after(joinPoint, result);

        return result;
    }

    private void before(JoinPoint joinPoint) {
        log.info(
                "Starting to execute method \"{}\", with args: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    private void after(JoinPoint joinPoint, Object result) {
        log.info(
                "Method \"{}\" was successfully done, result: {}",
                joinPoint.getSignature().getName(),
                result);
    }
}
