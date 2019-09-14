package com.tec.anji.www.core.validate.code.service;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {

    String SESSION_KEY = "SESSION_KEY_FOR_CODE_";

    void create(ServletWebRequest request) throws Exception;

    void validate(ServletWebRequest request);
}
