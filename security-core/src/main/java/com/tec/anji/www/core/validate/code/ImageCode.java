package com.tec.anji.www.core.validate.code;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@Data
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, long expireSeconds) {
        super(code, expireSeconds);
        this.image = image;
    }
}
