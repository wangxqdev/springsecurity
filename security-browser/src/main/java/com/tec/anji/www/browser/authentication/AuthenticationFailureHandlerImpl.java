package com.tec.anji.www.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tec.anji.www.core.properties.LoginType;
import com.tec.anji.www.core.properties.SecurityProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    private Log log = LogFactory.getLog(getClass());

    private ObjectMapper objectMapper;

    private SecurityProperties securityProperties;

    @Autowired
    public AuthenticationFailureHandlerImpl(ObjectMapper objectMapper, SecurityProperties securityProperties) {
        this.objectMapper = objectMapper;
        this.securityProperties = securityProperties;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            try (PrintWriter printWriter = response.getWriter()) {
                printWriter.write(objectMapper.writeValueAsString(exception));
                printWriter.flush();
            }
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
