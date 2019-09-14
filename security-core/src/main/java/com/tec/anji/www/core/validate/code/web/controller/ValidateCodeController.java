package com.tec.anji.www.core.validate.code.web.controller;

import com.tec.anji.www.core.validate.code.service.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class ValidateCodeController {

    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    @Autowired
    public ValidateCodeController(Map<String, ValidateCodeProcessor> validateCodeProcessors) {
        this.validateCodeProcessors = validateCodeProcessors;
    }

    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        validateCodeProcessors.get(type + "CodeProcessor").create(new ServletWebRequest(request, response));
    }
}
