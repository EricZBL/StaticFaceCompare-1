package com.hzgc.manage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 用户参数校验 (JSR303参数校验)
 * created by liang on 2018/11/16
 */
@Data
@ApiModel(value="UserPwdDto账号密码修改对象",description="账号密码修改对象入参")
public class UserPwdDto implements Serializable {


    /**
     * 登录账号ID
     */
    @NotEmpty(message = "登录账号userId不能为空")
    @ApiModelProperty(value="登录账号userId",name="userId",example="")
    private String userId;

    /**
     * 账号ID
     */
    @NotEmpty(message = "账号ID不能为空")
    @ApiModelProperty(value="账号id",name="id",example="")
    private String id;

    /**
     * 用户原始密码
     */
    @NotEmpty(message = "账号原始密码不能为空")
    @ApiModelProperty(value="账号原始密码Password",name="originpwd",example="123456")
    private String originpwd;

    /**
     * 用户密码（账号密码）
     */
    @NotEmpty(message = "账号新始密码不能为空")
    @ApiModelProperty(value="账号新密码newPassword",name="newPassword",example="123456")
    private String newPassword;

}
