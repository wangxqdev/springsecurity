package com.tec.anji.www.core.validate.code.web.controller;

import com.tec.anji.www.core.validate.code.ImageCode;
import com.tec.anji.www.core.validate.code.SmsCode;
import com.tec.anji.www.core.validate.code.ValidateCode;
import com.tec.anji.www.core.validate.code.service.SmsCodeSender;
import com.tec.anji.www.core.validate.code.service.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_VALIDATE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private ValidateCodeGenerator imageCodeGenerator;

    private ValidateCodeGenerator smsCodeGenerator;

    private SmsCodeSender smsCodeSender;

    @Autowired
    public ValidateCodeController(ValidateCodeGenerator imageCodeGenerator, ValidateCodeGenerator smsCodeGenerator, SmsCodeSender smsCodeSender) {
        this.imageCodeGenerator = imageCodeGenerator;
        this.smsCodeGenerator = smsCodeGenerator;
        this.smsCodeSender = smsCodeSender;
    }

    @GetMapping("/code/image")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request, response), SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws ServletRequestBindingException {
        SmsCode smsCode = (SmsCode) smsCodeGenerator.generate(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request, response), SESSION_KEY, smsCode);
        smsCodeSender.send(ServletRequestUtils.getRequiredStringParameter(request, "mobile"), smsCode.getCode());
    }
}
