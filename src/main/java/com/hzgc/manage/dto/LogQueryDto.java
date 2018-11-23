package com.hzgc.manage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 日志列表页参数校验 (JSR303参数校验)
 * created by liang on 2018/11/16
 */
@Data
@ApiModel(value="LogQueryDto日志查询对象",description="日志列表对象查询入参")
public class LogQueryDto implements Serializable {

    /**
     * 登录账号ID
     */
    @NotEmpty(message = "登录账号userId不能为空")
    @ApiModelProperty(value="登录账号userId",name="userId",example="")
    private String userId;
    /**
     * 账号名称
     */
    @ApiModelProperty(value="账号名称",name="username",example="")
    private String username;

    /**
     * 用户创建时间
     */
    @Field( type = FieldType.Date, format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createtime;

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