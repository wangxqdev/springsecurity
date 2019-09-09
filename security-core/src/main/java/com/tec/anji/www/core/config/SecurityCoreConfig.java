package com.tec.anji.www.core.config;

import com.tec.anji.www.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
