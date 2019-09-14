package com.tec.anji.www.core.validate.code.service.impl;

import com.tec.anji.www.core.validate.code.ImageCode;
import com.tec.anji.www.core.validate.code.service.AbstractValidateCodeProcessor;
import com.tec.anji.www.core.validate.code.service.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Map;

@Component
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Autowired
    public ImageCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators) {
        super(validateCodeGenerators);
    }

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws IOException {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
