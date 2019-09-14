package com.tec.anji.www.core.validate.code.service.impl;

import com.tec.anji.www.core.validate.code.SmsCode;
import com.tec.anji.www.core.validate.code.service.AbstractValidateCodeProcessor;
import com.tec.anji.www.core.validate.code.service.SmsCodeSender;
import com.tec.anji.www.core.validate.code.service.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@Component
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<SmsCode> {

    private SmsCodeSender smsCodeSender;

    @Autowired
    public SmsCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators, SmsCodeSender smsCodeSender) {
        super(validateCodeGenerators);
        this.smsCodeSender = smsCodeSender;
    }

    @Override
    protected void send(ServletWebRequest request, SmsCode smsCode) throws ServletRequestBindingException {
        smsCodeSender.send(ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile"), smsCode.getCode());
    }
}
