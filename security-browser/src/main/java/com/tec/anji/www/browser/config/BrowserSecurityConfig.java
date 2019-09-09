package com.tec.anji.www.browser.config;

import com.tec.anji.www.core.properties.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SecurityProperties securityProperties;

    public BrowserSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, SecurityProperties securityProperties) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        默认登录方式
//        http.httpBasic()
//        表单登录方式
        http.formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require"
                        , securityProperties.getBrowser().getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

    /**
     * encode() 存至数据库前使用
     * matches() 系统自动调用
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
