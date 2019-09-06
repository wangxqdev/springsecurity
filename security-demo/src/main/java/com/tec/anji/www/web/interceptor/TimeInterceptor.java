package com.tec.anji.www.web.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class TimeInterceptor implements HandlerInterceptor {

    private Log log = LogFactory.getLog(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Time Interceptor preHandle");
        log.info(((HandlerMethod) handler).getBean().getClass().getName());
        log.info(((HandlerMethod) handler).getMethod().getName());
        request.setAttribute("startTime", new Date().getTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("Time Interceptor postHandle");
        long startTime = (Long) request.getAttribute("startTime");
        log.info("Time Interceptor expends " + (new Date().getTime() - startTime) + "ms");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("Time Interceptor afterCompletion");
        long startTime = (Long) request.getAttribute("startTime");
        log.info("Time Interceptor expends " + (new Date().getTime() - startTime) + "ms");
        log.info("ex is " + ex);
    }
}
