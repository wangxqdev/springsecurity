package com.tec.anji.www.core.validate.code.web.filter;

import com.tec.anji.www.core.properties.SecurityConstants;
import com.tec.anji.www.core.properties.SecurityProperties;
import com.tec.anji.www.core.validate.code.ValidateCodeType;
import com.tec.anji.www.core.validate.code.exception.ValidateCodeException;
import com.tec.anji.www.core.validate.code.service.ValidateCodeProcessorHolder;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType type = getValidateCodeType(request);
        if (null != type) {
            logger.info("校验请求[" + request.getRequestURI() + "]中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            Stream.of(StringUtils.splitByWholeSeparator(urlString, ",")).forEach(url -> urlMap.put(url, type));
        }
    }

    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeTypeHolder holder = new ValidateCodeTypeHolder();
        if (!StringUtils.equalsIgnoreCase(request.getRequestURI(), "GET")) {
            urlMap.forEach((url, type) -> {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    holder.setValidateCodeType(type);
                }
            });
        }
        return holder.getValidateCodeType();
    }

    @Data
    private class ValidateCodeTypeHolder {

        private ValidateCodeType validateCodeType;
    }
}
