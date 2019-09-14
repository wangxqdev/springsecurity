package com.tec.anji.www.core.validate.code.service.impl;

import com.tec.anji.www.core.validate.code.service.SmsCodeSender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

public class DefaultSmsCodeSender implements SmsCodeSender {

    private Log log = LogFactory.getLog(getClass());

    @Override
    public void send(String mobile, String code) {
        log.info("向手机[" + mobile + "]发送验证码: " + code);
    }
}
