package com.tec.anji.www.core.validate.code.service;

import com.tec.anji.www.core.validate.code.ValidateCode;
import com.tec.anji.www.core.validate.code.ValidateCodeType;
import com.tec.anji.www.core.validate.code.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@SuppressWarnings({"unchecked", "unused"})
public abstract class AbstractValidateCodeProcessor<E extends ValidateCode> implements ValidateCodeProcessor {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        E validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    @Override
    public void validate(ServletWebRequest request) {
        ValidateCodeType type = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);

        E codeInSession = (E) sessionStrategy.getAttribute(request, getSessionKey(request));
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), type.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException(getClass(), "获取验证码失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(getClass(), "验证码不能为空");
        }

        if (null == codeInSession) {
            throw new ValidateCodeException(getClass(), "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(getClass(), "验证码已过期");
        }

        if (!StringUtils.equalsIgnoreCase(codeInRequest, codeInSession.getCode())) {
            throw new ValidateCodeException(getClass(), "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }

    private E generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type.concat(ValidateCodeGenerator.class.getSimpleName());
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (null == validateCodeGenerator) {
            throw new ValidateCodeException(getClass(), "验证码生成器[" + generatorName + "]不存在");
        }
        return (E) validateCodeGenerator.generate(request.getRequest());
    }

    private void save(ServletWebRequest request, E validateCode) {
        sessionStrategy.setAttribute(request, getSessionKey(request), validateCode);
    }

    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
    }

    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        return ValidateCodeType.valueOf(getProcessorType(request).toUpperCase());
    }

    private String getSessionKey(ServletWebRequest request) {
        return ValidateCodeProcessor.SESSION_KEY.concat(getValidateCodeType(request).toString().toUpperCase());
    }

    protected abstract void send(ServletWebRequest request, E validateCode) throws Exception;
}
