package com.tec.anji.www.core.validate.code.service;

import com.tec.anji.www.core.validate.code.ImageCode;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeGenerator {

    ImageCode generate(HttpServletRequest request);
}
