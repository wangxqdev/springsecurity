package com.tec.anji.www.core.validate.code.service.impl;

import com.tec.anji.www.core.properties.SecurityProperties;
import com.tec.anji.www.core.properties.SmsCodeProperties;
import com.tec.anji.www.core.validate.code.SmsCode;
import com.tec.anji.www.core.validate.code.ValidateCode;
import com.tec.anji.www.core.validate.code.service.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(HttpServletRequest request) {
        SmsCodeProperties sms = securityProperties.getCode().getSms();
        return new SmsCode(RandomStringUtils.randomNumeric(sms.getLength()), sms.getExpireSeconds());
    }
}
