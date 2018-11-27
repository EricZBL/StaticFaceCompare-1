package com.hzgc.manage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 用户登录参数校验 (JSR303参数校验)
 * created by liang on 2018/11/16
 */
@Data
@ApiModel(value="user账号登录",description="账号登录入参")
public class UserLoginDto implements Serializable {

    /**
     * 用户姓名（账号名称）
     */
    @NotEmpty(message = "账号名称不能为空")
    @ApiModelProperty(value="账号名称",name="username",example="admin")
    private String username;

    /**
     * 用户密码（账号密码）
     */
    @NotEmpty(message = "账号密码不能为空")
    @ApiModelProperty(value="账号名称",name="password",example="123456")
    private String password;



}
