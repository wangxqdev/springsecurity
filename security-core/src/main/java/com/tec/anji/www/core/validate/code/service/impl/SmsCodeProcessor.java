package com.tec.anji.www.core.validate.code.service.impl;

import com.tec.anji.www.core.validate.code.SmsCode;
import com.tec.anji.www.core.validate.code.service.AbstractValidateCodeProcessor;
import com.tec.anji.www.core.validate.code.service.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<SmsCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, SmsCode smsCode) throws ServletRequestBindingException {
        smsCodeSender.send(ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile"), smsCode.getCode());
    }
}
