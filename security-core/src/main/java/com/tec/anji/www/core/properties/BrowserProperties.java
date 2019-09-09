package com.tec.anji.www.core.properties;

import lombok.Data;

@Data
public class BrowserProperties {

    private String loginPage = "/anji-signIn.html";

    private LoginType loginType = LoginType.JSON;
}
