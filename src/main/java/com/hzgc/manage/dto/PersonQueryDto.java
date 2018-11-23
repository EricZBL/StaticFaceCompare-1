package com.hzgc.manage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 人口实体对象 (JSR303参数校验)
 * created by liang on 2018/11/16
 */
@Data
public class PersonQueryDto implements Serializable {

    /**
     * 登录账号ID
     */
    @NotEmpty(message = "登录账号userId不能为空")
    @ApiModelProperty(value="登录账号userId",name="userId",example="")
    private String userId;
    /**
     * 身份证（sfz）
     */
    @ApiModelProperty(value="身份证",name="sfz",example="")
    private String sfz;

    /**
     * 姓名（xm）
     */
    @ApiModelProperty(value="姓名",name="xm",example="")
    private String xm;

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
