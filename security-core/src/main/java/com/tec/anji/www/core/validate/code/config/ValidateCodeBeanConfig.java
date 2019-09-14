package com.tec.anji.www.core.validate.code.config;

import com.tec.anji.www.core.properties.SecurityProperties;
import com.tec.anji.www.core.validate.code.service.SmsCodeSender;
import com.tec.anji.www.core.validate.code.service.ValidateCodeGenerator;
import com.tec.anji.www.core.validate.code.service.impl.DefaultSmsCodeSender;
import com.tec.anji.www.core.validate.code.service.impl.ImageCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        return new ImageCodeGenerator() {
            {
                setSecurityProperties(securityProperties);
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
