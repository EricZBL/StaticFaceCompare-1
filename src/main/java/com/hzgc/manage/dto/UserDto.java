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
@ApiModel(value="UserDto对象入参",description="账号对象入参")
public class UserDto implements Serializable {


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
    @ApiModelProperty(value="账号id",name="id",example="eat786e4a7wrg")
    private String id;


    public UserDto (){}

    public UserDto(@NotEmpty(message = "登录账号userId不能为空") String userId, @NotEmpty(message = "账号ID不能为空") String id) {
        this.userId = userId;
        this.id = id;
    }
}
