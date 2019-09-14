package com.tec.anji.www.core.validate.code.service;

import com.tec.anji.www.core.validate.code.ValidateCodeType;
import com.tec.anji.www.core.validate.code.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String processorName = type.concat(ValidateCodeProcessor.class.getSimpleName());
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessors.get(processorName);
        if (null == validateCodeProcessor) {
            throw new ValidateCodeException(getClass(), "验证码处理器[" + processorName + "]不存在");
        }
        return validateCodeProcessor;
    }
}
