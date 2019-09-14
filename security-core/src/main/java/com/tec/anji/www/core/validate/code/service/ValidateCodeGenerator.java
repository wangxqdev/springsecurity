package com.tec.anji.www.core.validate.code.service;

import com.tec.anji.www.core.validate.code.ImageCode;
import com.tec.anji.www.core.validate.code.ValidateCode;

import javax.servlet.http.HttpServletRequest;

public interface ValidateCodeGenerator {

    ValidateCode generate(HttpServletRequest request);
}
