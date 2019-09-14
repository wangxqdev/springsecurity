package com.tec.anji.www.core.validate.code.service;

public interface SmsCodeSender {

    void send(String mobile, String code);
}
