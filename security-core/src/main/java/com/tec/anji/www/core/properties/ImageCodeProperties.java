package com.tec.anji.www.core.properties;

import lombok.Data;

@Data
public class ImageCodeProperties {

    private String formatName = "JPEG";

    private int width = 67;

    private int height = 23;

    private int length = 4;

    private long expireSeconds = 60L;

    private String url;
}
