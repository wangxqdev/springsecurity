package com.tec.anji.www.core.validate.code.exception;

import lombok.Data;
import org.springframework.security.core.AuthenticationException;

@Data
public class ValidateCodeException extends AuthenticationException {

    private Class<?> clazz;

    public ValidateCodeException(Class<?> clazz, String msg) {
        super(msg);
        this.clazz = clazz;
    }
}
