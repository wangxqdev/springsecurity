package com.tec.anji.www.web.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 配置自定义过滤器
 */
//@Component
public class TimeFilter implements Filter {

    private Log log = LogFactory.getLog(getClass());

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("Time Filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Time Filter start");
        long start = new Date().getTime();
        chain.doFilter(request, response);
        log.info("Time Filter expends " + (new Date().getTime() - start) + "ms");
        log.info("Time Filter end");
    }

    @Override
    public void destroy() {
        log.info("Time Filter destroy");
    }
}
