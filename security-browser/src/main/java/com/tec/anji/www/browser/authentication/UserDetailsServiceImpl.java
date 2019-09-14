package com.tec.anji.www.browser.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private Log log = LogFactory.getLog(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *
     * @param username username
     * @return UserDetails
     * <p>
     *     enabled: 账户是否可用
     *     accountNonExpired: 账户是否过期
     *     credentialsNonExpired: 密码是否过期
     *     accountNonLocked: 账户是否冻结
     * </p>
     * @throws UsernameNotFoundException UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录用户名: " + username);
        String password = passwordEncoder.encode("123456");
        log.info("数据库密码: " + password);
        return new User(username, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
