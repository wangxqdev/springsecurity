package com.tec.anji.www.core.validate.code.service;

import com.tec.anji.www.core.validate.code.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractValidateCodeProcessor<E extends ValidateCode> implements ValidateCodeProcessor {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    public AbstractValidateCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators) {
        this.validateCodeGenerators = validateCodeGenerators;
    }

    @Override
    public void create(ServletWebRequest request) throws Exception {
        E validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    @SuppressWarnings("unchecked")
    private E generate(ServletWebRequest request) {
        return (E) validateCodeGenerators.get(getProcessorType(request) + "CodeGenerator").generate(request.getRequest());
    }

    private void save(ServletWebRequest request, E validateCode) {
        sessionStrategy.setAttribute(request, ValidateCodeProcessor.SESSION_KEY + getProcessorType(request).toUpperCase(), validateCode);
    }

    private String getProcessorType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
    }

    protected abstract void send(ServletWebRequest request, E validateCode) throws Exception;
}
