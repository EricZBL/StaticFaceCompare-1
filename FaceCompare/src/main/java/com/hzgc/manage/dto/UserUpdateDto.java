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

@ApiModel(value="user账号对象",description="账号对象修改入参")
@Data
public class UserUpdateDto implements Serializable {

    /**
     * 登录账号ID
     */
    @NotEmpty(message = "登录账号userId不能为空")
    @ApiModelProperty(value="登录账号userId",name="userId",example="")
    private String userId;

    /**
     * 人口ID
     */
    @NotEmpty(message = "账号ID不能为空")
    @ApiModelProperty(value="账号ID",name="id",example="")
    private String id;

    /**
     * 用户姓名（账号名称）
     */
    @NotEmpty(message = "账号名称不能为空")
    @ApiModelProperty(value="账号名称",name="username",example="梁世伟")
    private String username;

    /**
     * 用户状态（账号状态，1 启用  2 禁用）
     */
    private int status;

}
