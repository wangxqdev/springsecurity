package com.tec.anji.www.core.validate.code.web.filter;

import com.tec.anji.www.core.properties.SecurityProperties;
import com.tec.anji.www.core.validate.code.ImageCode;
import com.tec.anji.www.core.validate.code.SmsCode;
import com.tec.anji.www.core.validate.code.exception.ValidateCodeException;
import com.tec.anji.www.core.validate.code.service.ValidateCodeProcessor;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SecurityProperties securityProperties;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Set<String> urlSet = new HashSet<String>() {
        {
            add("/authentication/mobile");
        }
    };

    public SmsCodeFilter(AuthenticationFailureHandler authenticationFailureHandler, SecurityProperties securityProperties) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.securityProperties = securityProperties;
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparator(securityProperties.getCode().getSms().getUrl(), ",");
        if (ArrayUtils.isNotEmpty(configUrls)) {
            urlSet.addAll(Stream.of(configUrls).collect(Collectors.toSet()));
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equalsIgnoreCase("post", request.getMethod())
                && urlSet.stream().anyMatch(url -> antPathMatcher.match(url, request.getRequestURI()))) {
            try {
                validate(new ServletWebRequest(request, response));
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        SmsCode codeInSession = (SmsCode) sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY.concat("SMS"));
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        if (null == codeInSession) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY.concat("SMS"));
            throw new ValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equalsIgnoreCase(codeInRequest, codeInSession.getCode())) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY.concat("SMS"));
    }
}