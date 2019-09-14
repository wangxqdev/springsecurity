package com.tec.anji.www.core.properties;

import lombok.Data;

@Data
public class SmsCodeProperties {

    private int length = 6;

    private long expireSeconds = 60L;

    private String url;
}
