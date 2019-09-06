package com.tec.anji.www.web.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class TimeAspect {

    private Log log = LogFactory.getLog(getClass());

    @Around("execution(* com.tec.anji.www.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        log.info("Time Aspect start");
        Arrays.stream(pjp.getArgs()).forEach(arg -> log.info("arg is " + arg));
        long startTime = new Date().getTime();
        Object ret = pjp.proceed();
        log.info("Time Aspect expends " + (new Date().getTime() - startTime) + "ms");
        log.info("Time Aspect end");
        return ret;
    }
}
