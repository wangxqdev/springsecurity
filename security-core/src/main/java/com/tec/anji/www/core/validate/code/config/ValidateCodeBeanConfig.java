package com.tec.anji.www.core.validate.code.config;

import com.tec.anji.www.core.properties.SecurityProperties;
import com.tec.anji.www.core.validate.code.service.ValidateCodeGenerator;
import com.tec.anji.www.core.validate.code.service.impl.ImageCodeGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {

    private SecurityProperties securityProperties;

    public ValidateCodeBeanConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    @ConditionalOnMissingClass("imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        return new ImageCodeGenerator(securityProperties);
    }
}
