package com.tec.anji.www.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tec.anji.www.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private Log log = LogFactory.getLog(getClass());

    //    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation("用户查询服务")
    public List<User> query(@RequestParam(name = "username", required = false, defaultValue = "jojo") String nickname,
                            @PageableDefault Pageable pageable) {
        log.info(nickname);
        log.info(ReflectionToStringBuilder.toString(pageable, ToStringStyle.MULTI_LINE_STYLE));
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        return userList;
    }

    //    @RequestMapping(value = "/user/{id:\\d+}", method = RequestMethod.GET)
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    @ApiOperation("用户明细服务")
    public User getInfo(@ApiParam("用户ID") @PathVariable(name = "id") String id) {
        log.info(id);
        return new User(id, "jojo", null, null);
//        throw new RuntimeException("User not exists");
//        throw new UserNotExistsException(id);
    }

    @PostMapping
    @ApiOperation("用户添加服务")
    public User create(@RequestBody User user) {
        log.info(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        return new User("1", user.getUsername(), user.getPassword(), user.getBirthday());
    }

    @PutMapping("/{id:\\d+}")
    @ApiOperation("用户修改服务")
    public User update(@ApiParam("用户ID") @PathVariable String id, @Valid @RequestBody User user, @ApiParam("错误处理") BindingResult result) {
        log.info(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        result.getAllErrors().forEach(error -> {
            FieldError fieldError = (FieldError) error;
            log.info(fieldError.getField() + " " + error.getDefaultMessage());
        });
        user.setId(id);
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    @ApiOperation("用户删除服务")
    public void delete(@ApiParam("用户ID") @PathVariable String id) {
        log.info(id);
    }

    @GetMapping("/me")
    @ApiOperation("获取用户信息")
    public Object currentUser(@AuthenticationPrincipal UserDetails userDetails){// Authentication authentication) {
//        return SecurityContextHolder.getContext().getAuthentication();
//        return authentication;
        return userDetails;
    }
}
