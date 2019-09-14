package com.tec.anji.www.core.validate.code.service.impl;

import com.tec.anji.www.core.validate.code.ImageCode;
import com.tec.anji.www.core.validate.code.service.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws IOException {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
