package com.ablez.jookbiren.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeAop {
    @Pointcut("execution(* com.ablez.jookbiren..episode..controller..*.*(..))")
    private void cut() {}

    @Around("cut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        log.info("실행 시간 : " + (end - start) + "ms");
        return  result;
    }
}
