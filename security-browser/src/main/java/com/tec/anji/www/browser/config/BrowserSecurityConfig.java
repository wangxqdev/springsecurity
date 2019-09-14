package com.tec.anji.www.browser.config;

import com.tec.anji.www.core.authentication.mobile.SmsAuthenticationSecurityConfig;
import com.tec.anji.www.core.properties.SecurityProperties;
import com.tec.anji.www.core.validate.code.web.filter.ImageCodeFilter;
import com.tec.anji.www.core.validate.code.web.filter.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SecurityProperties securityProperties;

    private PersistentTokenRepository tokenRepository;

    private DataSource dataSource;

    private UserDetailsService userDetailsService;

    private SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;

    @Autowired
    @Lazy
    public BrowserSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler,
                                 AuthenticationFailureHandler authenticationFailureHandler,
                                 SecurityProperties securityProperties,
                                 PersistentTokenRepository tokenRepository,
                                 DataSource dataSource,
                                 UserDetailsService userDetailsService,
                                 SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.securityProperties = securityProperties;
        this.tokenRepository = tokenRepository;
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
        this.smsAuthenticationSecurityConfig = smsAuthenticationSecurityConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ImageCodeFilter imageCodeFilter = new ImageCodeFilter(authenticationFailureHandler, securityProperties);
        imageCodeFilter.afterPropertiesSet();
        SmsCodeFilter smsCodeFilter = new SmsCodeFilter(authenticationFailureHandler, securityProperties);
        smsCodeFilter.afterPropertiesSet();
//        默认登录方式
//        http.httpBasic()
//        表单登录方式
        http.addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .rememberMe()
                .tokenRepository(tokenRepository)
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",
                        "/authentication/mobile",
                        securityProperties.getBrowser().getLoginPage(),
                        "/code/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .apply(smsAuthenticationSecurityConfig);
    }

    /**
     * @return PasswordEncoder
     * @see PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        return new JdbcTokenRepositoryImpl() {
            {
                setDataSource(dataSource);
//                setCreateTableOnStartup(true);
            }
        };
    }
}
