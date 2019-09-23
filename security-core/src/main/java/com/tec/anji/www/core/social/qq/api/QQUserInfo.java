package com.tec.anji.www.core.social.qq.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class QQUserInfo {

    private String ret;

    private String msg;

    private String nickname;

    private String figureurl;

    private String figureurl_1;

    private String figureurl_2;

    private String figureurl_qq_1;

    private String figureurl_qq_2;

    private String gender;

    private String is_yellow_vip;

    private String vip;

    private String yellow_vip_level;

    private String level;

    private String is_yellow_year_vip;
}
