package com.tec.anji.www.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s" +
            "&openId=%s";

    private Log log = LogFactory.getLog(getClass());

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String result = getRestTemplate().getForObject(String.format(URL_GET_OPENID, accessToken), String.class);
        log.info(result);
        this.openId = StringUtils.substringBetween(result, "openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() throws Exception {
        String result = getRestTemplate().getForObject(String.format(URL_GET_USER_INFO, appId, openId), String.class);
        log.info(result);
        return objectMapper.readValue(result, QQUserInfo.class);
    }
}
