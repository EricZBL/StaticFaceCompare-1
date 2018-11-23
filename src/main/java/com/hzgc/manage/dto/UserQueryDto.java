package com.hzgc.manage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 用户列表页参数校验 (JSR303参数校验)
 * created by liang on 2018/11/16
 */
@Data
@ApiModel(value="UserQueryDto账号查询对象",description="账号列表对象查询入参")
public class UserQueryDto implements Serializable {

    /**
     * 登录账号ID
     */
    @NotEmpty(message = "登录账号userId不能为空")
    @ApiModelProperty(value="登录账号userId",name="userId",example="")
    private String userId;

    /**
     * 姓名（username）
     */
    @ApiModelProperty(value="账号名称",name="username",example="admin")
    private String username;

    /**
     * 当前页
     */
    @NotEmpty(message = "页码不能为空")
    @ApiModelProperty(value="当前页码",name="page",example="0")
    private int page;

    /**
     * 每页大小
     */
    @NotEmpty(message = "每页大小不能为空")
    @ApiModelProperty(value="每页大小",name="size",example="10")
    private int size;
}