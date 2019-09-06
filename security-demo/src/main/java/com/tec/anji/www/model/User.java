package com.tec.anji.www.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.tec.anji.www.validate.MyConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class User {

    public interface UserSimpleView {}

    public interface UserDetailView extends UserSimpleView {}

    @JsonView(UserSimpleView.class)
    @ApiModelProperty("用户ID")
    private String id;

    @MyConstraint
    @JsonView(UserSimpleView.class)
    @ApiModelProperty("用户名")
    private String username;

    @NotBlank
    @JsonView(UserDetailView.class)
    @ApiModelProperty("密码")
    private String password;

    @Past
    @JsonView(UserSimpleView.class)
    @ApiModelProperty("生日")
    private Date birthday;
}
