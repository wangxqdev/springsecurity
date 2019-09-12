package com.tec.anji.www.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tec.anji.www.core.properties.LoginType;
import com.tec.anji.www.core.properties.SecurityProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {

    private Log log = LogFactory.getLog(getClass());

    private ObjectMapper objectMapper;

    private SecurityProperties securityProperties;

    @Autowired
    public AuthenticationSuccessHandlerImpl(ObjectMapper objectMapper,
                                            SecurityProperties securityProperties) {
        this.objectMapper = objectMapper;
        this.securityProperties = securityProperties;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        log.info("登录成功");
        response.setContentType("application/json;charset=UTF-8");
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            try (PrintWriter printWriter = response.getWriter()) {
                printWriter.write(objectMapper.writeValueAsString(authentication));
                printWriter.flush();
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
