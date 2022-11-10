package com.rore.demo.components;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.rore.demo.*.*(..) )")
    public void before(JoinPoint point) {
        String className = point.getTarget().getClass().toString();
        String methodName = point.getSignature().getName();
        Object[] array = point.getArgs();

        if (null != className && null != methodName && null != array){
            log.info(className+" : "+methodName+ "() arguments :"+ Arrays.toString(array));
        }
    }

    @AfterReturning(pointcut = "execution(* com.rore.demo.*.*(..) )", returning = "result")
    public void after(JoinPoint point, Object result) {
        String className = point.getTarget().getClass().toString();
        String methodName = point.getSignature().getName();

        if (null != className && null != methodName && null != result){
            log.info(className+" : "+methodName+ "() response :"+ result.toString());
        }
    }

}